package com.future.web;

import com.future.common.ResultObject;
import com.future.constant.StatusCode;
import com.future.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LoginService loginService;

    //小程序登录接口
    @RequestMapping(value= "/xcxLogin",method=RequestMethod.POST)
    @ResponseBody
    public ResultObject xcxLogin(HttpServletRequest request, HttpServletResponse response){
        String passKey=request.getParameter("passKey");
        String code=request.getParameter("code");
        if(StringUtils.isEmpty(passKey)&&StringUtils.isEmpty(code)){
            return new ResultObject(StatusCode.NULL.getCode());
        }
    	Map data= loginService.xcxLogin(passKey,code);
        if(data==null){
            new ResultObject(StatusCode.DEAL_FAIL.getCode());
        }
        return new ResultObject(StatusCode.SUCCESS.getCode(),StatusCode.SUCCESS.getMsg(),data);
    }
}