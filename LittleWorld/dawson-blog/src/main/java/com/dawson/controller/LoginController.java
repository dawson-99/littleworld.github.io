package com.dawson.controller;


import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {


    @Autowired
    LoginService loginService;
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }


}
