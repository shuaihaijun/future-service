package com.future;


import com.future.service.LoginService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminTests {

	@Autowired
	LoginService loginService;

	@Test
	public void testMap(){
		String passkey="";
		String code="re423432432";
		loginService.xcxLogin(passkey,code);

	}
}
