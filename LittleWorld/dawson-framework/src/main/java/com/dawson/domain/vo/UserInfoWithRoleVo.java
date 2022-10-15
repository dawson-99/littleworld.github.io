package com.dawson.domain.vo;


import com.dawson.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoWithRoleVo {

    UserInfoVo userInfoVo;
    List<Long> roleds;
    List<Role> roles;

}
