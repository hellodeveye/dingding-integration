package com.xiaocm.integration.sync.manager;

import com.xiaocm.integration.sync.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserManager {
    private Map<String, User> users = new HashMap<>();


    // 构造函数，用于从数据库初始化用户数据
    public UserManager(List<User> initialUsers) {
        users = initialUsers.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
    }


    public void sync(List<User> externalUsers) {
        Map<String, User> externalUserMap = externalUsers.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        // 更新或添加用户
        for (User externalUser : externalUsers) {
            addOrUpdateUser(externalUser);
        }

        // 删除不再存在的用户
        users.keySet().removeIf(id -> !externalUserMap.containsKey(id));
    }

    public void addOrUpdateUser(User user) {
        users.put(user.getId(), user);
    }

    public void removeUser(String userId) {
        users.remove(userId);
    }

    public User findById(String id) {
        return users.get(id);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public List<User> findByDepartmentId(String departmentId) {
        return users.values().stream()
                .filter(user -> user.getDepartmentIds().contains(departmentId))
                .collect(Collectors.toList());
    }

    public void updateUserDepartments(String userId, List<String> departmentIds) {
        User user = findById(userId);
        if (user != null) {
            user.setDepartmentIds(new ArrayList<>(departmentIds));
        }
    }

    public int getUserCount() {
        return users.size();
    }

    public boolean exists(String userId) {
        return users.containsKey(userId);
    }

    public List<User> findByName(String name) {
        return users.values().stream()
                .filter(user -> user.getName().contains(name))
                .collect(Collectors.toList());
    }

    public void clear() {
        users.clear();
    }

    // 用于测试和调试
    @Override
    public String toString() {
        return "UserManager{" +
                "userCount=" + getUserCount() +
                ", users=" + users.values() +
                '}';
    }
}
