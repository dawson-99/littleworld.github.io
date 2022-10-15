package com.dawson.Controller;


import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.LoginUser;
import com.dawson.domain.entity.Menu;
import com.dawson.domain.entity.User;
import com.dawson.domain.vo.AdminUserInfoVo;
import com.dawson.domain.vo.RoutersVo;
import com.dawson.domain.vo.UserInfoVo;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import com.dawson.service.*;
import com.dawson.utils.BeanCopyUtils;
import com.dawson.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Api(tags = "管理员登录登出接口的相关接口")
public class AdminLoginController {


    @Autowired
    AdminLoginService adminLoginService;

    @Autowired
    RoleMenuService roleMenuService;

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    /**
     * 用户登录接口
     */
    @ApiOperation(value = "登录接口")
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }

        return adminLoginService.login(user);
    }

    /**
     * 注销接口
     */
    @ApiOperation(value = "注销接口")
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }

    /**
     * 获取管理员信息接口，也就是获取登录用户的信息
     */
    @ApiOperation(value = "获取管理员信息接口")
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){

        //管理员信息
         LoginUser admin = SecurityUtils.getLoginUser();
         // 管理员权限
         List<String> perms = menuService.selectPermsById(admin.getUser().getId());

         //管理员角色信息
         List<String> roleKeys = roleService.selectRoleKeyByUserId(admin.getUser().getId());

         //获取用户信息
         UserInfoVo userInfoVo = BeanCopyUtils.copyBean(admin.getUser(), UserInfoVo.class);
         //封装
         AdminUserInfoVo adminUserInfoVo = new  AdminUserInfoVo(perms,roleKeys, userInfoVo);
         return ResponseResult.okResult(adminUserInfoVo);
    }
    /**
     * 获取用户登录进去以后，可以使用的功能接口
     */
    @ApiOperation(value = "功能分配接口")
    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        //查询菜单列表
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));

    }






}
