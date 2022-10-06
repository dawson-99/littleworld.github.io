package com.dawson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-10-05 19:16:05
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
