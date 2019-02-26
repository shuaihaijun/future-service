package com.future.service;

import com.future.entity.User;
import com.future.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AdminService {

    Logger log= LoggerFactory.getLogger(AdminService.class);

    @Autowired
    UserMapper userMapper;

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    public void save(User user){

        if(user==null||user.getOpenId()==null){
            log.error("error: 参数为空！");
            return;
        }

        if(user.getPasskey()==null){
            user.setPasskey(UUID.randomUUID().toString());
        }
        /*这里填充必要值*/

        userMapper.insertSelective(user);

    }

    /**
     * 修改用户信息
     * @param user
     */
    public void update(User user){

        return;
    }

    /**
     * 根据条件查询用户信息
     * @param map
     * @return
     */
    public List<User> getUserByCondition(Map map){

        if(map==null){
            log.error("error: 参数为空！");
            return null;
        }

        /*这里校验必要参数*/

        return userMapper.selectByCondition(map);
    }

    /**
     * 保存或更新用户信息（根据openId）
     * @param user
     */
    public void saveOrUpdateByOpenId(User user){
        if(user==null||user.getOpenId()==null){
            log.error("error: 参数为空！");
            return;
        }

        Map map=new HashMap();
        map.put("openId",user.getOpenId());

        List<User> users=getUserByCondition(map);

        if(users==null||users.size()==0){
            //save
            save(user);
        }else {
            //update
            update(user);
        }
    }
}
