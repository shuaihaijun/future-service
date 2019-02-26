package com.future.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.future.constant.XcxErrCode;
import com.future.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    Logger log= LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AdminService adminService;

    /**
     * 小程序登录接口
     * @param passkey 用户登录凭证
     * @param code  用户登录凭证（有效期五分钟）
     * @return
     */
    public Map xcxLogin(String passkey,String code){
        Map data=new HashMap();

        String appId="wx3ae220e3348eada3";
        String secret="d8f0d79559f26641c0fd0b2cc57802f6";
        String openid="";
        String sessionKey="";
        String unionid="";
        String url="https://api.weixin.qq.com/sns/jscode2session";
        url+=url+"?appid="+appId;
        url+=url+"&&secret="+secret;
        url+=url+"&js_code"+code;
        url+=url+"&grant_type=authorization_code";

        String result=restTemplate.postForObject(url,null,String.class);

        if(StringUtils.isEmpty(result)){
            log.error("登录失败 result:"+result);
            throw new RuntimeException("登录失败！");
        }

        JSONObject resultJson= JSON.parseObject(result);
        String errcode=resultJson.getString("errcode");
        if(XcxErrCode.SUCCESS.getCode().equalsIgnoreCase(errcode)){
            openid=resultJson.getString("unionid");
            sessionKey=resultJson.getString("session_key");
            unionid=resultJson.getString("unionid");
        }else {
            throw new RuntimeException(XcxErrCode.valueOf(errcode).getMsg());
        }

        User user=new User();
        user.setOpenId(openid);
        user.setSessionKey(sessionKey);
        user.setUnionId(unionid);

        adminService.saveOrUpdateByOpenId(user);

        data.put("passkey",user.getPasskey());
        return data;
    }
}
