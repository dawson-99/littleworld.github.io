package com.dawson.runner;

import com.dawson.domain.entity.Article;
import com.dawson.mapper.ArticleMapper;
import com.dawson.service.ArticleService;
import com.dawson.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ArticleViewCountRunner implements CommandLineRunner {


    @Autowired
    RedisCache redisCache;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleMapper articleMapper;


    @Override
    //这个方法是重写的，就是spring启动后就会执行
    public void run(String... args) throws Exception {

        //先把所有文章查询出来
       List<Article> articleList = articleService.list(null);

       //变成map集合
       Map<String, Integer> viewCounts = articleList.stream()
               .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
       //存进redis
        redisCache.setCacheMap("article:viewCount", viewCounts);
    }
}
