package com.future.mapper;


import com.future.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    List<User> selectByCondition(Map map);
}