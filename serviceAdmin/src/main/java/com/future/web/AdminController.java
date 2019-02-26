package com.future.web;

import com.future.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LoginService loginService;

    //小程序登录接口
    @RequestMapping(value= "/sLogin",method=RequestMethod.POST)
    @ResponseBody
    public Map sLogin(HttpServletRequest request, HttpServletResponse response){
    	return loginService.xcxLogin(null,null);
    }
}