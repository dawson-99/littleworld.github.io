package com.dawson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-10-12 09:46:40
 */
public interface MenuService extends IService<Menu> {

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<String> selectPermsById(Long id);

    ResponseResult menuList(Menu menu);

    boolean hasChild(Long menuId);

    ResponseResult getMenuTree();

    ResponseResult RoleMenuTreeselect(Long roleId);
}
