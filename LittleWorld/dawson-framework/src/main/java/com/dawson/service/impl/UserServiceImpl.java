package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.domain.vo.UserInfoVo;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import com.dawson.mapper.UserMapper;
import com.dawson.service.UserService;
import com.dawson.utils.BeanCopyUtils;
import com.dawson.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public ResponseResult getUserInfo() {

        //获取用户的id，用之前封住好的工具类
        User user = SecurityUtils.getLoginUser().getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfoUpdate(User user) {

        Long userId = SecurityUtils.getUserId();
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, userId)
                .set(StringUtils.hasText(user.getAvatar()),User::getAvatar, user.getAvatar())
                .set(StringUtils.hasText(user.getEmail()),User::getEmail, user.getEmail())
                .set(StringUtils.hasText(user.getNickName()),User::getNickName, user.getNickName())
                .set(StringUtils.hasText(user.getSex()), User::getSex, user.getSex());
        update(updateWrapper);
        return ResponseResult.okResult("成功更新个人信息");
    }

    @Override
    public ResponseResult register(User user) {

        //检查是否有问题，如果有问题，下面抛出异常后，是不会执行的
        Check_Has_Error(user);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        this.save(user);
        return ResponseResult.okResult();
    }

    //封装的检查用户注册信息是否合法
    private void Check_Has_Error(User user){
        //先检查是否为空
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        } else if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        } else if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        } else if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }

        //再检查一下用户的名字是否重了.假设就只检查用户名的唯一性
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName());

        int count = count(queryWrapper);
        if(count > 0){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
    }


}


