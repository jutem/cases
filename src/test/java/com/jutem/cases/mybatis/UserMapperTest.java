package com.jutem.cases.mybatis;

import com.alibaba.fastjson.JSON;
import com.jutem.cases.base.BaseTest;
import com.jutem.cases.mybatis.mapper.UserMapper;
import com.jutem.cases.mybatis.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapperTest extends BaseTest{
    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUser() {
        User user = userMapper.getUser(1L);
        System.out.println(JSON.toJSONString(user));
    }

}
