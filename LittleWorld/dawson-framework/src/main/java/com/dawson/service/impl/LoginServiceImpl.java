package com.dawson.service.impl;

import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class LoginServiceImpl implements LoginService {


    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {


        UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);



        return null;
    }
}
