package com.future.web;

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
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/login")
public class ComputeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Registration registration;       // 服务注册
    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/user" ,method = RequestMethod.GET)
    public String user(@RequestParam Integer a, @RequestParam Integer b) {
        List<ServiceInstance> instance = client.getInstances(registration.getServiceId());
        Integer r = a + b;
        logger.info(a+":"+b);
        logger.info("/add, host:" + instance.get(0).getPort()+ ", service_id:" + instance.get(0).getServiceId() + ", result:" + r);
        return "From servicePay, Result is " + r;
    }

    //A服务调用B服务
    @RequestMapping(value="test",method=RequestMethod.GET)
    public String test(){
        List<ServiceInstance> instance = client.getInstances(registration.getServiceId());
        logger.info("/add, host:" + instance.get(0).getPort()+ ", service_id:" + instance.get(0).getServiceId());
        return "From servicePay, Result is ";
    }
    
}