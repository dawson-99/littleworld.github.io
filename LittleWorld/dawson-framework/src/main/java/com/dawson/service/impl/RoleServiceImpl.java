package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.constant.SystemConstants;
import com.dawson.domain.DTO.AlterRoleDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Role;
import com.dawson.domain.entity.RoleMenu;
import com.dawson.domain.vo.PageVo;
import com.dawson.domain.vo.RoleVo;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import com.dawson.mapper.RoleMapper;
import com.dawson.service.RoleMenuService;
import com.dawson.service.RoleService;
import com.dawson.utils.BeanCopyUtils;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMenuService roleMenuService;


    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //查看是不是管理员

        if (id == 1) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
        }
        //其它用户，查询用户信息
        return getBaseMapper().selectRoleKeysByUserId(id);
    }

    @Override
    public ResponseResult selectRoleList(Long pageNum, Long pageSize, String roleName, String status) {

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status), Role::getStatus, status)
                .like(StringUtils.hasText(roleName), Role::getRoleName, roleName);

        Page<Role> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult alterRole(AlterRoleDto alterRoleDto) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(Role::getId, alterRoleDto.getRoleId())
                .set(Role::getStatus, alterRoleDto.getStatus());

        update(updateWrapper);
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult addRole(Role role) {

        //先把角色添加进表
        save(role);
        // 再把功能添加进role_menu表
        List<RoleMenu> roleMenus = Arrays.stream(role.getMenuIds())
                .map(f -> {
                    return new RoleMenu(role.getId(), f);
                }).collect(Collectors.toList());
        if (role.getMenuIds().length > 0 && role.getMenuIds() != null)
            roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleInfo(Long id) {

        Role role = getById(id);

        if( role == null){
            throw new SystemException(AppHttpCodeEnum.ROLE_NOT_EXIST);
        }
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    @Transactional//涉及到两个表的修改，所以设定为事务
    public ResponseResult alterRoleInfo(Role role) {

        //更新角色信息，但是权限没有更新
        updateById(role);
        //再更新权限信息.首先将以前的删除，再添加进去新的权限
        Map<String, Object> map = new HashMap();
        map.put("role_id", role.getId());
        roleMenuService.removeByMap(map);
        List<RoleMenu> roleMenus = Arrays.stream(role.getMenuIds())
                        .map( f -> {
                           return new RoleMenu(role.getId(), f);
                        }).collect(Collectors.toList());

        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);

        List<Role> roles = list(queryWrapper);

        return ResponseResult.okResult(roles);
    }


}
