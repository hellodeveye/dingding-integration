package com.xiaocm.integration.sync.domain.convertor;

import com.xiaocm.integration.sync.domain.DataConvertor;
import com.xiaocm.integration.sync.domain.Department;
import com.xiaocm.integration.sync.domain.DingTalkDepartment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DingTalkDepartmentConvertor implements DataConvertor<DingTalkDepartment, Department> {
    @Override
    public Department map(DingTalkDepartment source) {
        return null;
    }

    @Override
    public List<Department> mapList(List<DingTalkDepartment> sourceList) {
        return List.of();
    }
}
