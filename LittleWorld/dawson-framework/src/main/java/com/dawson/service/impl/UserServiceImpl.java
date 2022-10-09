package com.dawson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.domain.vo.UserInfoVo;
import com.dawson.mapper.UserMapper;
import com.dawson.service.UserService;
import com.dawson.utils.BeanCopyUtils;
import com.dawson.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult getUserInfo() {

        //获取用户的id，用之前封住好的工具类
        User user = SecurityUtils.getLoginUser().getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }
}


