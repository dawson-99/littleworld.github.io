package com.dawson.Controller;


import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
@Api(tags = "用户相关类，包括改变用户权限等")
public class UserController {


    @Autowired
    UserService userService;


    /**
     * 分页查询用户信息
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询用户信息")
    public ResponseResult listUsers(Long pageNum,
                                    Long pageSize,
                                    String userName,
                                    String phonenumber,
                                    String status){

          return userService.listUsers(pageNum, pageSize, userName, phonenumber, status);
    }

    /**
     * 新增用户
     */
    @PostMapping
    @ApiOperation(value = "新增用户")
    public ResponseResult addUser(@RequestBody User user){
        userService.register(user);
        return ResponseResult.okResult();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    /**
     * 查询用户，返回角色信息等
     */

    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询用户")
    public ResponseResult getUserByUserId(@PathVariable("id") Long userId){
          return userService.getUserByUserId(userId);
    }

    /**
     * 修改用户，包括权限等
     */
    @PutMapping
    @ApiOperation(value = "修改用户信息，包括权限信息")
    public ResponseResult alterUser(@RequestBody User user){
        return userService.alterUser(user);
    }



}
