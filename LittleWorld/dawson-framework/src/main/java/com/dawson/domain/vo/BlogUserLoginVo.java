package com.dawson.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这个类是登录成功后返回到前段页面的user信息的
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserLoginVo {
    String token;
    private UserInfoVo userInfoVo;

}
