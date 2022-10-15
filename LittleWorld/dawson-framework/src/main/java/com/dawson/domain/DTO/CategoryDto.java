package com.dawson.domain.DTO;

import com.baomidou.mybatisplus.annotation.TableId;

public class CategoryDto {

    //名字
    private String name;
    //描述
    private String description;
    //状态0:正常,1禁用
    private String status;

}
