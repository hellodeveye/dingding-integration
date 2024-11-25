package com.xiaocm.integration.sync.aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.xiaocm.integration.sync.domain.Department;
import com.xiaocm.integration.sync.domain.Position;
import com.xiaocm.integration.sync.domain.Role;
import com.xiaocm.integration.sync.domain.User;
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

    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    public void addUser(User user) {
        this.users.add(user);
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
    public String getId() { return id; }
    public String getName() { return name; }
    public List<Department> getDepartments() { return new ArrayList<>(departments); }
    public List<User> getUsers() { return new ArrayList<>(users); }
    public List<Role> getRoles() { return new ArrayList<>(roles); }
    public List<Position> getPositions() { return new ArrayList<>(positions); }
}
