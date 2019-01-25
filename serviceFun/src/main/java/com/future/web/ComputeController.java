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

import java.util.List;

@RestController
public class ComputeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
    private Registration registration;


    @Autowired
    private DiscoveryClient client;
    
    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public String add(@RequestParam Integer a, @RequestParam Integer b) {
    	
		List<ServiceInstance> instance = client.getInstances(registration.getServiceId());
		Integer r = a + b;
		logger.info("/add, host:" + instance.get(0).getHost() + ", service_id:" + instance.get(0).getServiceId() + ", result:" + r);

        return "调用次数超限！";
    }

}