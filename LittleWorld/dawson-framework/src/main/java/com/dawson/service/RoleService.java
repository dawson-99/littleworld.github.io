package com.dawson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dawson.domain.DTO.AlterRoleDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult selectRoleList(Long pageNum, Long pageSize, String roleName, String status);

    ResponseResult alterRole(AlterRoleDto alterRoleDto);

    ResponseResult addRole(Role role);

    ResponseResult getRoleInfo(Long id);

    ResponseResult alterRoleInfo(Role role);

    ResponseResult listAllRole();
}
