package com.jutem.cases.mybatis.mapper;

import com.jutem.cases.mybatis.model.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{userId}")
    User getUser(Long userId);
}
