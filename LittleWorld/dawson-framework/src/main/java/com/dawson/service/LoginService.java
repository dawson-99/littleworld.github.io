package com.dawson.service;

import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);
}
