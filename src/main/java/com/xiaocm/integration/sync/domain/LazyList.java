package com.xiaocm.integration.sync.domain;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class LazyList<T> extends AbstractList<T> {
    private List<T> internalList;
    private Supplier<Collection<T>> loader;
    private boolean loaded = false;

    public LazyList(Supplier<Collection<T>> loader) {
        this.loader = loader;
    }

    private void loadIfNecessary() {
        if (!loaded) {
            synchronized (this) {
                if (!loaded) {
                    Collection<T> loadedData = loader.get();
                    internalList = new ArrayList<>(loadedData);
                    loaded = true;
                    loader = null; // 允许 GC 回收 loader
                }
            }
        }
    }

    @Override
    public T get(int index) {
        loadIfNecessary();
        return internalList.get(index);
    }

    @Override
    public int size() {
        loadIfNecessary();
        return internalList.size();
    }

    @Override
    public boolean add(T element) {
        loadIfNecessary();
        return internalList.add(element);
    }

    @Override
    public T set(int index, T element) {
        loadIfNecessary();
        return internalList.set(index, element);
    }

    @Override
    public T remove(int index) {
        loadIfNecessary();
        return internalList.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        loadIfNecessary();
        return internalList.remove(o);
    }

    @Override
    public void clear() {
        if (loaded) {
            internalList.clear();
        } else {
            loaded = true;
            internalList = new ArrayList<>();
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    // 如果需要序列化，可以添加自定义的序列化和反序列化方法
}