package com.dawson.controller;


import com.dawson.annotation.SystemLog;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户信息类")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "获取用户信息")
//    @SystemLog(businessName = "获取用户信息")
    public ResponseResult getUserInfo(){
        return userService.getUserInfo();
    }

    @PostMapping("/updateUserInfoUpdate")
    @ApiOperation(value = "更新用户信息")
//    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfoUpdate(@RequestBody User user){
         return userService.updateUserInfoUpdate(user);
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册接口")
//    @SystemLog(businessName = "注册接口")
    public  ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
