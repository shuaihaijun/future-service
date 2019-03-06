package com.future.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@ApiModel(value = "/user",description = "用户中心")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Registration registration;       // 服务注册
    @Autowired
    private DiscoveryClient client;

    @ApiOperation(value = "用户登录接口！")
    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public String user(@RequestParam @ApiParam(value = "用户名") String username, @RequestParam @ApiParam(value = "密码") String password) {
        List<ServiceInstance> instance = client.getInstances(registration.getServiceId());
        logger.info("/add, host:" + instance.get(0).getPort()+ ", service_id:" + instance.get(0).getServiceId() + ", result:");
        return "From servicePay, Result is ";
    }

    //A服务调用B服务
    @ApiOperation("调用测试方法！")
    @RequestMapping(value="test",method=RequestMethod.GET)
    public String test(){
        List<ServiceInstance> instance = client.getInstances(registration.getServiceId());
        logger.info("/add, host:" + instance.get(0).getPort()+ ", service_id:" + instance.get(0).getServiceId());
        return "From servicePay, Result is ";
    }
    
}