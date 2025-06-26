package org.example.onlinecontact.client.login;

import org.example.onlinecontact.exception.LoginErrorException;
import org.example.onlinecontact.mapper.UserMapper;
import org.example.onlinecontact.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Scanner;

@Component // 标记为 Spring 组件
public class Login {
    @Autowired
    private UserMapper userMapper; // 非静态字段，由 Spring 注入

    public User login(String username, String password) {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = User.builder().passWord(password).userName(username).build();

        user=userMapper.login(user);
        if (user != null) {
            return user;
        } else {
            user = User.builder().passWord(password).userName(username).build();
            if(userMapper.getByUserName(username)!=0){user.setId(-2); return user;}
            userMapper.insert(user);
            return user;
        }
    }
}