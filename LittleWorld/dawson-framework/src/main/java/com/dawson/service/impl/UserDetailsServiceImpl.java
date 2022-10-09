package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dawson.domain.entity.LoginUser;
import com.dawson.domain.entity.User;
import com.dawson.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 这个类是SpringSecurity认证结构里面的最后一层filter。因为
 * 默认的filter是把内容存储在内存上的查询的，这里我们重新实现最
 * 后一层接口，把它放在数据库里面查询.
 *
 *
 * 根据官网的描述，这里就是让我们重新写的。因为默认是从内存的数据
 * 中查询，官网让我们重新写，用mysql、或者redis进行查询。然后返
 * 回从数据库中查询到的用户信息，后面会在其他的地方进行比较
 *
 * 原文如下：
 * It has various implementations CachingUserDetailsService,
 * JDBCDaoImpl etc. Based on the implementation an appropriate
 * UserDetailsService is called.It is responsible for fetching
 * the User Object with username and password against which the
 * incoming User Object will be compared.
 *
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        //这里我们从数据库里面查询，不再从内存中查询。也就是重写的意义所在
        User user = userMapper.selectOne(queryWrapper);

        if(Objects.isNull(user)){
            //这里先抛出来一个runtime的异常类（主要是其他类我写不来哈哈哈哈哈）。在SpringS
            // ecurity的认证过程中，所有的异常都会被检测到的
            throw new RuntimeException("用户不存在");
        }
        // TODO 权限信息

        //这里的user必须封装成为loginUser才可以，因为返回的类型必须是UserDetails
        return new LoginUser(user);
    }
}
