package com.dawson.controller;


import com.dawson.annotation.SystemLog;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Article;
import com.dawson.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "文章")
@RestController//这个注解可以让里面所有的方法，都返回成为网络可传输的模式。就是把数据转换成为json格式，进行了一次格式化
@RequestMapping("/article")//以下所有的方法，前面都加的前缀，例如访问list，那么就是localhost：7777/article/list。端口号已经在yml文件里面设置成7777
public class ArticleController {

    @Autowired//自动注入
    ArticleService articleService;

    //此接口用于查询热门文章
    @GetMapping("hotArticleList")
    @ApiOperation(value = "查询热门文章")
//    @SystemLog(businessName = "查询热门文章")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    /**
     * 此接口用于查询文章列表。表现为点击一个分类，就出现该分类的文章
     */
    @GetMapping("/articleList")
    @ApiOperation(value = "查询文章列表")
//    @SystemLog(businessName = "查询文章列表")
    public ResponseResult ArticleList(Integer pageNum, Integer pageSize, Long categoryId){
        return articleService.getArticleList(pageNum, pageSize, categoryId);
    }

    /**
     * 此接口用于根据id查询文章
     * pathVarible 和 param的区别就是：pathVarible是放在路径上面的，用/来分开。但是param是？来区分的
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "用id获取文章")
//    @SystemLog(businessName = "用id获取文章")
    public ResponseResult getArticleById(@PathVariable("id") Long id){
          return articleService.getArticleById(id);
    }


    /**
     * put请求。通常就是用在把redis原有的数据修改的。反正我的理解是这样，把redis数据给修改了，没有增加，也没有减少。
     */
    @PutMapping("/updateViewCount/{id}")
    @ApiOperation(value = "将文章的浏览次数加1")
//    @SystemLog(businessName = "将文章的浏览次数加1")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
