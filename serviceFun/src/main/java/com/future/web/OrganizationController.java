package com.future.web;

import com.future.common.ResultObject;
import com.future.constant.StatusCode;
import com.future.entity.Organization;
import com.future.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/org")
public class OrganizationController {

    private final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    OrganizationService organizationService;
    
    @RequestMapping(value = "/getBannar" ,method = RequestMethod.POST)
    @ResponseBody
    public ResultObject getBannar() {
        List<Organization> orgs=organizationService.getOrgBanner();
        return new ResultObject(StatusCode.SUCCESS.getCode(),StatusCode.SUCCESS.getMsg(),orgs);
    }

}