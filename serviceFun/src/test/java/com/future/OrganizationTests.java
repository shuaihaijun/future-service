package com.future;


import com.future.entity.Organization;
import com.future.service.OrganizationService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationTests {

	@Autowired
	OrganizationService organizationService;

	@Test
	public void testLogin(){
		List<Organization> orgs=organizationService.getOrgBanner();
		String passkey="";
		String code="081077XY1f1BLS050TUY1KjiXY1077Xb";
	}
}
