package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Role;
import com.dawson.domain.entity.RoleMenu;
import com.dawson.domain.entity.User;
import com.dawson.domain.entity.UserRole;
import com.dawson.domain.vo.PageVo;
import com.dawson.domain.vo.UserInfoVo;
import com.dawson.domain.vo.UserInfoWithRoleVo;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import com.dawson.mapper.UserMapper;
import com.dawson.service.RoleService;
import com.dawson.service.UserRoleService;
import com.dawson.service.UserService;
import com.dawson.utils.BeanCopyUtils;
import com.dawson.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

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
    @Transactional
    public ResponseResult register(User user) {

        //检查是否有问题，如果有问题，下面抛出异常后，是不会执行的
        Check_Has_Error(user);
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        this.save(user);

        // TODO 赋予权限,已完成
        if(user.getRoleIds() != null && user.getRoleIds().length > 0){
            List<UserRole> userRoles = Arrays.stream(user.getRoleIds())
                    .map( f -> {
                        return  new UserRole(user.getId(), f);
                    }).collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listUsers(Long pageNum, Long pageSize, String userName, String phonenumber, String status) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.hasText(status),User::getStatus, status)
                .like(StringUtils.hasText(userName),User::getUserName, userName)
                .like(StringUtils.hasText(phonenumber),User::getPhonenumber, phonenumber);

        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUserByUserId(Long userId) {

        //查询用户信息
        User user  = getById(userId);
        //提取某些信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //查询所有角色信息
        List<Role> roles = roleService.list();
        //查询用户拥有的角色代号
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        //转换成string的
        List<Long> userRolesToLong = userRoles.stream()
                .map( f -> {
                    return f.getRoleId();
                }).collect(Collectors.toList());

        UserInfoWithRoleVo userInfoWithRoleVo = new UserInfoWithRoleVo(userInfoVo, userRolesToLong, roles);
        return ResponseResult.okResult(userInfoWithRoleVo);
    }

    @Override
    @Transactional
    public ResponseResult alterUser(User user) {

        //先修改用户的信息
        save(user);
        //移除用户的role标记
        Map<String, Object> map = new HashMap();
        map.put("user_id", user.getId());
        userRoleService.removeByMap(map);
        //再加新的进去
        List<UserRole> userRoles = Arrays.stream(user.getRoleIds())
                .map( f -> {
                    return new UserRole(user.getId(), f);
                }).collect(Collectors.toList());
        //保存
        userRoleService.saveBatch(userRoles);
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

        long count = count(queryWrapper);
        if(count > 0){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
    }
}


