package com.dawson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;



public interface UserService extends IService<User> {

    ResponseResult getUserInfo();
}
