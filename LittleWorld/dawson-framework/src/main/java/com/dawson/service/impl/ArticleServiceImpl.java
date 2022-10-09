package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.constant.SystemConstants;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Article;
import com.dawson.domain.entity.Category;
import com.dawson.domain.vo.ArticleListVo;
import com.dawson.domain.vo.ArticleVo;
import com.dawson.domain.vo.HotArticleVo;
import com.dawson.domain.vo.PageVo;
import com.dawson.mapper.ArticleMapper;
import com.dawson.mapper.CategoryMapper;
import com.dawson.service.ArticleService;
import com.dawson.service.CategoryService;
import com.dawson.utils.BeanCopyUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    @Autowired
    CategoryService categoryService;



    /**
     * 此接口用于查询热门文章
     * 查询10条消息出来，并且按照浏览量由高到低
     */
    @Override
    public ResponseResult hotArticleList() {

        //mybatisplus中的查询封装工具。可以用来构造查询条件
        LambdaQueryWrapper<Article> QueryWrapper = new LambdaQueryWrapper<>();
        /**
         * 查询该文章是否已经发表，如果发表。0是已经发表状态，1是草稿状态
         *    eq就是equal的意思。这里的Article::getStatus本来该写成是status，相当于是
         * 数据库中的字段，但是为了防止写错，用实体来映射，就是现在这样子了。
         */
        QueryWrapper.eq(Article::getStatus, SystemConstants.STATUS_NORMAL);

        //降序排列
        QueryWrapper.orderByDesc(Article::getViewCount);

        /**
         * page是mybatis的分页工具。为什么要有分页呢？因为如果数据太多（假设几千万条），
         * 没必要一次性查出来，所以可以分页查询，相当于是给定一个坐标，然后从指定的坐标开
         * 始查后面的数据。
         * current就是指定的坐标，size就是从current开始后面要查的数据条数f
         */
        Page<Article> page = new Page(1,10);
        page(page,QueryWrapper);
        List<Article> hotArticles = page.getRecords();

        List<HotArticleVo> res = BeanCopyUtils.copyListBean(hotArticles, HotArticleVo.class);


        /**
         *         敲，其实都很垃圾16点
         *         for ( Article hot : hotArticles){
         *              HotArticleVo hotArticleVo = new HotArticleVo();
         *
         *              //这个方法可以将有相同属性的，一一对应赋值
         *              BeanUtils.copyProperties(hot,hotArticleVo);
         *
         *              //下面这个方法是真的垃圾，敲。15点
         *              hotArticleVo.setViewCount(hot.getViewCount());
         *              hotArticleVo.setTitle(hot.getTitle());
         *              hotArticleVo.setId(hot.getId());
         *              res.add(hotArticleVo);
         *         }
         */
        return ResponseResult.okResult(res);
    }


    /**
     * 此接口用于查询文章列表。表现为点击一个分类，就出现该分类的文章。只有文章的摘要，没有具体内容
     */
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, Long categoryId){

         //查询条件，categoryid有或者没有、是否正式文章，是否置顶
         LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

         //这一步的第一个参数是条件参数，如果满足就会执行，否则不执行
         queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,Article::getCategoryId, categoryId);
         //继续封装wrapper
         queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                 .orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        //取分类名字出来。因为article里面只有分类的id，但是没有分类id所对应的分类名。所以要单独再找一下

         //这是for方式
//        List<Article> articles = page.getRecords();
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //stream方式。这玩意儿就写法高端点，还不是一个个枚举。
                List<Article> articles = page.getRecords();
                articles.stream().map( f -> {
                    Category category = categoryService.getById(f.getCategoryId());
                    f.setCategoryName(category.getName());
                    return f;
                }).collect(Collectors.toList());

                //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyListBean(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 此接口用于根据id查询文章，包括文章的内容
     */
    public ResponseResult getArticleById(Long id){
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Article::getId, id);
        Article article = getById(id);
        ArticleVo articleVo = BeanCopyUtils.copyBean(article, ArticleVo.class);

        Category category = categoryService.getById(articleVo.getCategoryId());
        if(category != null){
            articleVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleVo);
    }
}
