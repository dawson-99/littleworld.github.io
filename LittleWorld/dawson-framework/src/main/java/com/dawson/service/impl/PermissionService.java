package com.dawson.service.impl;



import com.dawson.enums.AppHttpCodeEnum;

import com.dawson.exception.SystemException;
import com.dawson.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {

       public boolean hasPermission(String perm){

              Long userId = SecurityUtils.getUserId();

              //如果是超级管理员就返回真
              if(userId == 1L){
                     return true;
              }
              //其它就判断一下
              List<String> perms = SecurityUtils.getLoginUser().getPermissions();
              if( perms == null || !perms.contains(perm)) throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
              return true;
       }

}
