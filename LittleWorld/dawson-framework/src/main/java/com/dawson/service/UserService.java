package com.dawson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;


public interface UserService extends IService<User> {

    ResponseResult getUserInfo();

    ResponseResult updateUserInfoUpdate(User user);

    ResponseResult register(User user);

    ResponseResult listUsers(Long pageNum, Long pageSize, String userName, String phonenumber, String status);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserByUserId(Long userId);

    ResponseResult alterUser(User user);
}
