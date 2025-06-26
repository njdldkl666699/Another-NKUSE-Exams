package org.example.onlinecontact.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.onlinecontact.pojo.User;

import java.util.ArrayList;

@Mapper
public interface UserMapper {


    @Select("select * from user where user_name=#{userName} and password=#{passWord}")
    User login(User user);

    void insert(User user);
    @Select("select count(*) from user where user_name=#{username}")
    Integer getByUserName(String username);
    @Select("select user_name from user where id=#{id}")
    String getUsernameById(Integer id);
    @Select("select * from user where id!=#{id}")
    ArrayList<User> getExcludeId(Integer id);
}
