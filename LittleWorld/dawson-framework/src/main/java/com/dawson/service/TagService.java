package com.dawson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dawson.domain.DTO.TagDto;
import com.dawson.domain.DTO.TagListDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Tag;
import com.dawson.domain.vo.PageVo;
import com.dawson.domain.vo.TagVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-10-10 19:40:25
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagDto tagDto);

    ResponseResult deleteTag(Long id);

    ResponseResult showTag(Long id);

    ResponseResult alterTag(TagVo tagVo);

    ResponseResult listAllTag();
}
