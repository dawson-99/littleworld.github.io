package com.dawson.Controller;


import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Menu;
import com.dawson.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * 获取菜单列表。虽然但是我认为更像是相似查询
     */
    @GetMapping("/list")
    public ResponseResult list(Menu menu) {
        return menuService.menuList(menu);
    }

    /**
     *添加菜单
     */

    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    /**
     * 获取菜单
     */
    @GetMapping("{id}")
    public ResponseResult selectById(@PathVariable("id") Long id) {
        Menu menu = menuService.getById(id);
        return ResponseResult.okResult(menu);
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public ResponseResult edit(@RequestBody Menu menu) {
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{menuId}")
    public ResponseResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChild(menuId)) {
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }

    /**
     *  获取菜单树
     */
    @GetMapping("/treeselect")
    public ResponseResult getMenuTree(){
        return menuService.getMenuTree();
    }

    /**
     * 加载角色所对应的菜单树
     */
     @GetMapping("/roleMenuTreeselect/{roleId}")
    public ResponseResult RoleMenuTreeselect(@PathVariable("roleId") Long roleId){
         return menuService.RoleMenuTreeselect(roleId);
     }





}
