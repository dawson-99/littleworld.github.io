package com.dawson.service.impl;

import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.LoginUser;
import com.dawson.domain.entity.User;
import com.dawson.domain.vo.BlogUserLoginVo;
import com.dawson.domain.vo.UserInfoVo;
import com.dawson.service.AdminLoginService;
import com.dawson.utils.BeanCopyUtils;
import com.dawson.utils.JwtUtil;
import com.dawson.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        /**
         * 从登录开始将采取SpringSecurity进行设置。包括token，认证，授权等等
         */

        //采取manager进行认证
        UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //下面的这个manager最终会到重写后的UserDetailsServiceImpl中进行。也就是最后一层。下面这一步过后会把flag设置成true；
        Authentication authenticate = authenticationManager.authenticate(token);

        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或者密码错误");
        }

        //获取id并用jwt获得token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //存储在redis中，方便下一次查询
        redisCache.setCacheObject("adminlogin:" + userId, loginUser);

        //封装
        Map<String, String> res = new HashMap<>();
        res.put("token", jwt);
        return ResponseResult.okResult(res);
    }

    @Override
    public ResponseResult logout() {

        //因为之前在过滤的时候就
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        Long userId = loginUser.getUser().getId();

        redisCache.deleteObject("adminlogin:" + userId);


        return ResponseResult.okResult();
    }
}
