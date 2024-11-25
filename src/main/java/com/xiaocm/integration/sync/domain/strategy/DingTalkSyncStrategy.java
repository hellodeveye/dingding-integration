package com.xiaocm.integration.sync.domain.strategy;
import java.util.List;

import com.xiaocm.integration.sync.domain.*;
import com.xiaocm.integration.sync.domain.convertor.DingTalkDepartmentConvertor;
import com.xiaocm.integration.sync.domain.convertor.DingTalkRoleConvertor;
import com.xiaocm.integration.sync.domain.convertor.DingTalkUserConvertor;
import com.xiaocm.integration.sync.exception.EntityNotFoundException;
import com.xiaocm.integration.sync.repository.DingTalkClient;
import org.springframework.stereotype.Component;

import com.xiaocm.integration.sync.aggregate.OrganizationAggregate;
import com.xiaocm.integration.sync.service.OrganizationRepositoryService;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DingTalkSyncStrategy implements SyncStrategy {

    private final DingTalkClient dingTalkClient;
    private final OrganizationRepositoryService organizationService;
    private final DingTalkDepartmentConvertor dingTalkDepartmentConvertor;
    private final DingTalkUserConvertor dingTalkUserConvertor;
    private final DingTalkRoleConvertor dingTalkRoleConvertor;

    public DingTalkSyncStrategy(DingTalkClient dingTalkClient, OrganizationRepositoryService organizationService, DingTalkDepartmentConvertor dingTalkDepartmentConvertor, DingTalkUserConvertor userMapper, DingTalkRoleConvertor roleMapper) {
        this.dingTalkClient = dingTalkClient;
        this.organizationService = organizationService;
        this.dingTalkDepartmentConvertor = dingTalkDepartmentConvertor;
        this.dingTalkUserConvertor = userMapper;
        this.dingTalkRoleConvertor = roleMapper;
    }

    @Override
    public SyncType getSyncType() {
        return SyncType.DINGTALK;
    }

    @Override
    @Transactional
    public void sync(String organizationId) {
        OrganizationAggregate org = organizationService.findById(organizationId);
        if (org == null) {
            throw new EntityNotFoundException("Organization not found");
        }

        // 同步部门
        List<DingTalkDepartment> dingDepts = dingTalkClient.getDepartments();
        for (DingTalkDepartment dingDept : dingDepts) {
            Department dept = mapDepartment(dingDept);
            org.addDepartment(dept);
        }

        // 同步用户
        List<DingTalkUser> dingUsers = dingTalkClient.getUsers();
        for (DingTalkUser dingUser : dingUsers) {
            User user = mapUser(dingUser);
            org.addUser(user);
        }

        // 同步角色
        List<DingTalkRole> dingRoles = dingTalkClient.getRoles();
        for (DingTalkRole dingRole : dingRoles) {
            Role role = mapRole(dingRole);
            org.addRole(role);
        }

        // 保存更新后的组织
        organizationService.saveOrUpdate(org);
    }

    private Department mapDepartment(DingTalkDepartment dingDept) {
        // 实现钉钉部门到系统部门的映射逻辑
        return dingTalkDepartmentConvertor.map(dingDept);

    }

    private User mapUser(DingTalkUser dingUser) {
        return dingTalkUserConvertor.map(dingUser);
    }

    private Role mapRole(DingTalkRole dingRole) {
        // 实现钉钉角色到系统角色的映射逻辑
        return dingTalkRoleConvertor.map(dingRole);
    }
}