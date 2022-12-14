package com.dawson.enums;


//枚举类，这里可以看到，enum。主要是是用于统一返回的信息的，减少修改量。可以网上搜枚举类，来查看
public enum AppHttpCodeEnum {



    //下面SUCCESS等等都相当于一个对象，可以认为是一个AppHttpCodeEnum实例，每个实例包括code和msg两部分

    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    CONTENT_NOT_NULL(506, "评论内容不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误，请上传png、jpeg、jpg文件"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    TAGNAME_NOT_NULL(513,"标签名不能为空"),
    TAGREMARK_NOT_NULL(514,"备注不能为空"),
    ROLE_NOT_EXIST(515,"该角色不存在");

    //包括的两部分
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
