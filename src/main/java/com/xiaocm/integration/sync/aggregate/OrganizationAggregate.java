package com.xiaocm.integration.sync.aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.xiaocm.integration.sync.domain.*;
import com.xiaocm.integration.sync.vo.DepartmentId;
import com.xiaocm.integration.sync.vo.RoleId;
import com.xiaocm.integration.sync.vo.UserId;

public class OrganizationAggregate {
    private String id;
    private String name;
    private List<Department> departments;
    private List<User> users;
    private List<Role> roles;
    private List<Position> positions;

    // 私有构造函数，防止直接实例化
    private OrganizationAggregate(String id, String name) {
        this.id = id;
        this.name = name;
        this.departments = new ArrayList<>();
        this.users = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.positions = new ArrayList<>();
    }

    // 静态工厂方法创建新的组织
    public static OrganizationAggregate createNew(String name) {
        return new OrganizationAggregate(UUID.randomUUID().toString(), name);
    }

    // 静态工厂方法重建已存在的组织
    public static OrganizationAggregate reconstitute(String id, String name,
                                                     List<Department> departments,
                                                     List<User> users,
                                                     List<Role> roles,
                                                     List<Position> positions) {
        OrganizationAggregate org = new OrganizationAggregate(id, name);
        org.departments = departments;
        org.users = users;
        org.roles = roles;
        org.positions = positions;
        return org;
    }

    public void syncUsers(List<User> externalUsers) {
        Map<String, User> externalUserMap = externalUsers.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        // 更新或添加用户
        for (User externalUser : externalUsers) {
            addOrUpdateUser(externalUser);
        }

        // 删除不再存在的用户
        users.removeIf(user -> !externalUserMap.containsKey(user.getId()));
    }

    private void addOrUpdateUser(User externalUser) {
        users.stream()
                .filter(u -> u.getId().equals(externalUser.getId()))
                .findFirst()
                .ifPresentOrElse(
                        existingUser -> updateUser(existingUser, externalUser),
                        () -> users.add(externalUser)
                );
    }

    private void updateUser(User existingUser, User externalUser) {
        // 更新用户信息
//        existingUser.setName(externalUser.getName());
//        existingUser.setEmail(externalUser.getEmail());
        // ... 更新其他字段 ...
    }


    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void assignUserToDepartment(UserId userId, DepartmentId departmentId) {
        User user = findUserById(userId);
        if (user != null) {
            user.addDepartmentId(departmentId);
        }
    }

    public void assignRoleToUser(UserId userId, RoleId roleId) {
        User user = findUserById(userId);
        if (user != null) {
            user.addRoleId(roleId);
        }
    }

    private User findUserById(UserId userId) {
        return users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    // 其他方法...

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Department> getDepartments() {
        return new ArrayList<>(departments);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public List<Role> getRoles() {
        return new ArrayList<>(roles);
    }

    public List<Position> getPositions() {
        return new ArrayList<>(positions);
    }

    public <T> void setDepartments(LazyList<T> ts) {

    }

    public <T> void setUsers(LazyList<T> ts) {

    }

    public <T> void setRoles(LazyList<T> ts) {

    }

    public <T> void setPositions(LazyList<T> ts) {

    }
}
