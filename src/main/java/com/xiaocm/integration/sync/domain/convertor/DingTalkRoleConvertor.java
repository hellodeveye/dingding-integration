package com.xiaocm.integration.sync.domain.convertor;

import com.xiaocm.integration.sync.domain.DataConvertor;
import com.xiaocm.integration.sync.domain.DingTalkRole;
import com.xiaocm.integration.sync.domain.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DingTalkRoleConvertor implements DataConvertor<DingTalkRole, Role> {
    @Override
    public Role map(DingTalkRole source) {
        return null;
    }

    @Override
    public List<Role> mapList(List<DingTalkRole> sourceList) {
        return List.of();
    }
}
