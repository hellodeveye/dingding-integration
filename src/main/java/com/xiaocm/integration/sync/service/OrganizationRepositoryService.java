package com.xiaocm.integration.sync.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaocm.integration.sync.aggregate.OrganizationAggregate;
import com.xiaocm.integration.sync.domain.*;
import com.xiaocm.integration.sync.mapper.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganizationRepositoryService extends ServiceImpl<OrganizationMapper, Organization> {

    private final DepartmentMapper departmentMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PositionMapper positionMapper;

    public OrganizationRepositoryService(DepartmentMapper departmentMapper,
                                         UserMapper userMapper,
                                         RoleMapper roleMapper,
                                         PositionMapper positionMapper) {
        this.departmentMapper = departmentMapper;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.positionMapper = positionMapper;
    }

    @Transactional
    public void saveOrUpdate(OrganizationAggregate organization) {
        // 保存或更新组织
        Organization org = new Organization();
        super.saveOrUpdate(org);

        // 保存或更新部门
        List<Department> departments = organization.getDepartments();
        if (departments != null && !departments.isEmpty()) {
            for (Department dept : departments) {
                dept.setOrganizationId(organization.getId());
            }
            departmentMapper.insertOrUpdate(departments);
        }

        // 保存或更新用户
        List<User> users = organization.getUsers();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                user.setOrganizationId(organization.getId());
            }
            userMapper.insertOrUpdate(users);
        }

        // 保存或更新角色
        List<Role> roles = organization.getRoles();
        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                role.setOrganizationId(organization.getId());
            }
            roleMapper.insertOrUpdate(roles);
        }

        // 保存或更新岗位
        List<Position> positions = organization.getPositions();
        if (positions != null && !positions.isEmpty()) {
            for (Position position : positions) {
                position.setOrganizationId(organization.getId());
            }
            positionMapper.insertOrUpdate(positions);
        }
    }

    @Transactional(readOnly = true)
    public OrganizationAggregate findById(String id) {
        Organization byId = super.getById(id);
//
//
//        if (org != null) {
//
//            org.setDepartments(new LazyList<>(() -> departmentMapper.selectById(id)));
//            org.setUsers(new LazyList<>(() -> userMapper.selectById(id)));
//            org.setRoles(new LazyList<>(() -> roleMapper.selectById(id)));
//            org.setPositions(new LazyList<>(() -> positionMapper.selectById(id)));
//        }
        return null;
    }

    @Transactional
    public void deleteDepartment(String organizationId, String departmentId) {
        departmentMapper.deleteById(departmentId);
    }

    @Transactional
    public void deleteUser(String organizationId, String userId) {
        userMapper.deleteById(userId);
    }

    @Transactional
    public void deleteRole(String organizationId, String roleId) {
        roleMapper.deleteById(roleId);
    }

    @Transactional
    public void deletePosition(String organizationId, String positionId) {
        positionMapper.deleteById(positionId);
    }

    // 可以根据需要添加其他方法，如按条件查询等
}
