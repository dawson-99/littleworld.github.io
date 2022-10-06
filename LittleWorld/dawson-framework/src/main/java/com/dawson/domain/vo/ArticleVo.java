package com.dawson.domain.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //分类id
    private Long categoryId;
    //所属分类名
    private String categoryName;
    //文章内容
    private String content;
    //缩略图
    private String thumbnail;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;
    //创建时间
    private Date createTime;
}
