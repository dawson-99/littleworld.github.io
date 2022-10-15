package com.dawson.Controller;


import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.User;
import com.dawson.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {


    @Autowired
    UserService userService;


    /**
     * 分页查询用户信息
     */
    @GetMapping("/list")
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
    public ResponseResult addUser(@RequestBody User user){
        userService.register(user);
        return ResponseResult.okResult();
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    /**
     * 查询用户，返回角色信息等
     */

    @GetMapping("/{id}")
    public ResponseResult getUserByUserId(@PathVariable("id") Long userId){
          return userService.getUserByUserId(userId);
    }

    /**
     * 修改用户，包括权限等
     */
    @PutMapping
    public ResponseResult alterUser(@RequestBody User user){
        return userService.alterUser(user);
    }



}
