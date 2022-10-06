package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.constant.SystemConstants;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Article;
import com.dawson.domain.entity.Category;
import com.dawson.domain.vo.CategoryListVo;
import com.dawson.mapper.CategoryMapper;
import com.dawson.service.ArticleService;
import com.dawson.service.CategoryService;
import com.dawson.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-10-05 19:16:06
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    ArticleService articleService;


    /**
     * 这个接口用来返回已经发布的文章当中有哪些分类的，
     * 只需要返回分类的种类即可
     */
    @Override
    public ResponseResult getCategoryList() {

        //先去查询文章表，状态为已经发布的文章
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        articleQueryWrapper.eq(Article::getStatus, SystemConstants.STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleQueryWrapper);

        /**
         *  获取文章的分类id,并且去重
         *  set是一种特殊的数据结构，能够自动去重，保留唯一性。比如一个集合出现了两个apple，
         *  那就将会自动去除一个。反正c++里面就是这样的
         */
        Set<Long> categoryIds =  articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories.stream().filter(category -> category.getStatus().equals(SystemConstants.STATUS_NORMAL)).collect(Collectors.toList());
        //封装VO
        List<CategoryListVo> categoryListVoList = BeanCopyUtils.copyListBean(categories, CategoryListVo.class);
        return ResponseResult.okResult(categoryListVoList);
    }
}


