package com.dawson.controller;


import com.dawson.annotation.SystemLog;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import com.dawson.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "登录类")
public class LoginController {


    @Autowired
    private LoginService loginService;//由于登录操作设计的东西太多了，所以就单独设置一个登录的接口出来

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
//    @SystemLog(businessName = "登录接口")
    public ResponseResult login(@RequestBody User user){

        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }


    @PostMapping("/logout")
    @ApiOperation(value = "登出接口")
//    @SystemLog(businessName = "登出接口")
    public ResponseResult logout(){
        return loginService.logout();
    }


}
