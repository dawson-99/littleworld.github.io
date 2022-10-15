package com.dawson.Controller;


import com.dawson.domain.DTO.AlterRoleDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Role;
import com.dawson.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 获取用户角色
     */

    @GetMapping("/list")
    public ResponseResult selectRoleList(Long pageNum, Long pageSize, String roleName, String status) {
        return roleService.selectRoleList(pageNum, pageSize, roleName, status);
    }

    /**
     * 改变角色状态，停用或者开启
     */
    @PutMapping("/changeStatus")
    public ResponseResult alterRole(@RequestBody AlterRoleDto alterRoleDto) {
        return roleService.alterRole(alterRoleDto);
    }

    /**
     * 新添加角色，要添加可以用的功能
     */
    @PostMapping
    public ResponseResult addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    /**
     * 获得角色的信息
     */
    @GetMapping("/{id}")
    public ResponseResult getRoleInfo(@PathVariable Long id) {
        return roleService.getRoleInfo(id);
    }

    /**
     * 更新用户角色
     */
    @PutMapping
    public ResponseResult alterRoleInfo(@RequestBody Role role) {
         return roleService.alterRoleInfo(role);
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long roleId){
        roleService.removeById(roleId);
        return ResponseResult.okResult();
    }

    /**
     * 查询正常状态的角色
     */
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }

    /**
     *
     */


}
