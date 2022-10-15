package com.dawson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dawson.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 16:48:40
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeysByUserId(Long userId);
}
