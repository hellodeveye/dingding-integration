package com.xiaocm.integration.sync.domain;

import java.util.List;

public interface DataConvertor<S, T> {
    T map(S source);
    List<T> mapList(List<S> sourceList);
}
