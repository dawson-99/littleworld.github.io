package com.dawson.controller;


import com.dawson.annotation.SystemLog;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/userInfo")
    @SystemLog(businessName = "获取用户信息")
    public ResponseResult getUserInfo(){
        return userService.getUserInfo();
    }

    @PostMapping("/updateUserInfoUpdate")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfoUpdate(@RequestBody User user){
         return userService.updateUserInfoUpdate(user);
    }

    @PostMapping("/register")
    @SystemLog(businessName = "注册接口")
    public  ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
