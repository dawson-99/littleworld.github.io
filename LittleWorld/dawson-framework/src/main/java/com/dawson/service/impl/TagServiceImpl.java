package com.dawson.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.domain.entity.Tag;
import com.dawson.mapper.TagMapper;
import com.dawson.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-10 19:40:26
 */

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}



