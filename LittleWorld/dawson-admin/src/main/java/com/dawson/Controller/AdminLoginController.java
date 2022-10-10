package com.dawson.Controller;


import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import com.dawson.service.AdminLoginService;
import com.dawson.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AdminLoginController {


    @Autowired
    AdminLoginService adminLoginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        adminLoginService.login(user);
        return ResponseResult.okResult();
    }

//
//    @PostMapping("/user/logout")
//    public ResponseResult logout(){
//        return adminLoginService.logout();
//    }






}
