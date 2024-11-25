package com.xiaocm.integration.sync.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaocm.integration.sync.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
}
