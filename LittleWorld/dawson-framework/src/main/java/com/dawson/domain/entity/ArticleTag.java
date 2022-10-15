package com.dawson.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2022-10-14 12:04:14
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ds_article_tag")
public class ArticleTag  {
    //文章id
    private Long articleId;
    //标签id

    private Long tagId;

}
