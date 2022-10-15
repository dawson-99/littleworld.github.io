package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.constant.SystemConstants;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Menu;
import com.dawson.domain.vo.MenuTreeVo;
import com.dawson.domain.vo.MenuVo;
import com.dawson.domain.vo.RoleMenuTreeSelectVo;
import com.dawson.mapper.MenuMapper;
import com.dawson.mapper.RoleMapper;
import com.dawson.service.MenuService;
import com.dawson.utils.BeanCopyUtils;
import com.dawson.utils.SecurityUtils;
import com.dawson.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-12 09:46:40
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    @Autowired
    RoleMapper roleMapper;

    @Autowired
    MenuMapper menuMapper;



    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {

        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus;
        //如果是管理员
        if(SecurityUtils.isAdmin()){
             menus = menuMapper.selectAllRouterMenu();
        } else {
             menus = menuMapper.selectMenuTreeById(userId);
        }

        //构建树,就是设置二级菜单
        List<Menu> menuTree = builderMenuTree(menus, 0L);
        return menuTree;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> tree = menus.stream()
                .filter(f -> f.getParentId().equals(parentId))
                .map(f ->
                    f.setChildren(getChildren(menus,f))
                ).collect(Collectors.toList());
        return tree;
    }

    private List<Menu> getChildren(List<Menu> menus, Menu f) {
        List<Menu> children = menus.stream()
                .filter(menu -> menu.getParentId().equals(f.getId()))
                .map( menuf ->
                        //递归，假设有三级菜单，也要继续设置子节点
                    menuf.setChildren(getChildren(menus, menuf)))
                .collect(Collectors.toList());
        return children;
    }


    @Override
    public List<String> selectPermsById(Long id) {


        //如果是管理员，返回所有权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON)
                    .eq(Menu::getStatus, SystemConstants.NORMAL);
            List<Menu> menus = list(queryWrapper);

            List<String> perms = menus.stream()
                    .map(f -> {
                        return f.getPerms();
                    }).collect(Collectors.toList());
            return perms;
        }
        //如果是其他人，返回应有的权限
        List<String> perms = getBaseMapper().selectByPermsUserId(id);

        return perms;
    }

    @Override
    public ResponseResult menuList(Menu menu) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus, menu.getStatus())
                .like(StringUtils.hasText(menu.getMenuName()), Menu::getMenuName, menu.getMenuName())
                .orderByAsc(Menu::getParentId, Menu::getOrderNum);


        List<Menu> menus = list(queryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyListBean(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public boolean hasChild(Long menuId) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, menuId);

        if(count(queryWrapper) > 0){
            return true;
        }
        return false;
    }

    @Override
    public ResponseResult getMenuTree() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);

        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(menuTreeVos);
    }

    @Override
    public ResponseResult RoleMenuTreeselect(Long roleId) {

        //先获取菜单的id
        List<Long> menuIds = menuMapper.selectMenuIdByRoleId(roleId);
        //用菜单的id去查找
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        //转换成响应的vo
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        //封装成最后的VO
        RoleMenuTreeSelectVo roleMenuTreeSelectVo = new RoleMenuTreeSelectVo(menuIds, menuTreeVos);
        return ResponseResult.okResult(roleMenuTreeSelectVo);
    }


}


