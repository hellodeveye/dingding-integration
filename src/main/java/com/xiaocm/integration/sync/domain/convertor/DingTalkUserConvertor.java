package com.xiaocm.integration.sync.domain.convertor;

import com.xiaocm.integration.sync.domain.DataConvertor;
import com.xiaocm.integration.sync.domain.DingTalkUser;
import com.xiaocm.integration.sync.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DingTalkUserConvertor implements DataConvertor<DingTalkUser, User> {
    @Override
    public User map(DingTalkUser source) {
        return null;
    }

    @Override
    public List<User> mapList(List<DingTalkUser> sourceList) {
        return List.of();
    }
}
