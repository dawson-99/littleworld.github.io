package com.dawson;


import com.dawson.domain.entity.Article;
import com.dawson.domain.entity.User;
import com.dawson.mapper.ArticleMapper;
import com.dawson.service.ArticleService;
import com.dawson.service.UserService;
import com.dawson.service.impl.UserServiceImpl;
import com.dawson.utils.RedisCache;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class OssTest {

    @Autowired
    RedisCache redisCache;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void upload(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "GtUtkPhsdq8iH0MShvqIwLQ9jhBZw208yotP9u5n";
        String secretKey = "zhuWuBtJUnhPLdTyjDw2KF8fU9io0e2nLf_6yB3C";
        String bucket = "dawson-qiniuyun";

//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;

        try {
            InputStream inputStream = new FileInputStream("src/main/resources/img/FYWu4T5X0AIHstk.jpeg");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test(){
        UserService userService = new UserServiceImpl();
        User user = new User();
        user.setPassword("1233213");
        user.setNickName("dddd");
        user.setUserName("3213");
        userService.save(user);
    }

    @Test
    public void viewCount_To_Database(){

        //从redis中获取
        Map<String, Integer> viewMap = redisCache.getCacheMap("article:viewCount");

        Article article = new Article(1L,123);
        article.setId(1L);
        article.setViewCount(123L);
        List<Article> articles = new ArrayList<>();
        articles.add(article);


        //写到数据库里面
        articleMapper.updateById(article);
    }



}
