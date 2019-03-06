package com.future.service;

import com.future.entity.Organization;
import com.future.mapper.OrganizationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationService {


    @Autowired
    OrganizationMapper organizationMapper;


    /**
     * 获取首页显示组织banner
     * @return
     */
    public List<Organization> getOrgBanner(){
        Map map =new HashMap();
        map.put("orgType","0");
        PageHelper.startPage(1,5);
        List<Organization> orgs= organizationMapper.selectByCondition(map);
        PageInfo<Organization> pages=new PageInfo<>(orgs);
        return pages.getList();
//        return orgs;
    }
}
