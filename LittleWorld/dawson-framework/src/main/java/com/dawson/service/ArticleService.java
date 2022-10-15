package com.dawson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dawson.domain.DTO.AddArticleDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Article;
import com.dawson.domain.vo.ArticleAlterVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleById(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult selectArticle(Long pageNum, Long pageSize, String title, String summary);

    ResponseResult getArticleById_To_Alter(Long id);

    ResponseResult updateArticle(ArticleAlterVo articleAlterVo);

    ResponseResult deleteArticle(Long id);
}
