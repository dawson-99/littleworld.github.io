package com.dawson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.domain.entity.UserRole;
import com.dawson.mapper.UserRoleMapper;
import com.dawson.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-10-15 13:47:22
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}


