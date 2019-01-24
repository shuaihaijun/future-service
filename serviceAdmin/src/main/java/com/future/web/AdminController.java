package com.future.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RestTemplate restTemplate;
    @Value("${baseUrl}")
    public String dataUrl;


    //B服务调用A服务
    @RequestMapping(value= "/testConfig",method=RequestMethod.GET)
    public String testConfig(){
        logger.info(dataUrl);
        String loginUrl="http://servicePay/pays/testConfig";
        ResponseEntity<String> response= restTemplate.getForEntity(loginUrl,String.class);
    	return response.getBody()+"----------";
    }
}