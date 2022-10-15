package com.dawson.controller;


import com.dawson.annotation.SystemLog;
import com.dawson.domain.ResponseResult;
import com.dawson.service.CategoryService;
import com.dawson.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(tags = "类别")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping("/getCategoryList")
    @ApiOperation(value = "获取分类")
//    @SystemLog(businessName = "获取分类")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
