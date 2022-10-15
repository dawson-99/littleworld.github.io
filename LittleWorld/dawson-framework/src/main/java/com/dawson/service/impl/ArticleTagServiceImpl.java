package com.dawson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.domain.entity.ArticleTag;
import com.dawson.mapper.ArticleTagMapper;
import com.dawson.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-14 12:04:14
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}


