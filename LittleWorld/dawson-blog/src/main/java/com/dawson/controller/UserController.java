package com.dawson.controller;


import com.dawson.domain.ResponseResult;
import com.dawson.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/userInfo")
    public ResponseResult getUserInfo(){
        return userService.getUserInfo();
    }
}
