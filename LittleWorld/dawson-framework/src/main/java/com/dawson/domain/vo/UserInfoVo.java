package com.dawson.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 为了和token进行隔离，这里单独创建一个类出来，然后到loginVo里面二次封装。
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 年龄
     */
    private String sex;

    /**
     * 邮件
     */
    private String email;


}
