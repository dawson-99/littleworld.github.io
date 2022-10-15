package com.dawson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.domain.entity.RoleMenu;
import com.dawson.mapper.RoleMenuMapper;
import com.dawson.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-11 16:49:00
 */
@Service("roleMenuService")
//public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
//}

public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
}


