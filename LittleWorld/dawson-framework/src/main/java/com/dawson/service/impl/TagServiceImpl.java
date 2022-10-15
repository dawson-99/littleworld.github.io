package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.domain.DTO.TagDto;
import com.dawson.domain.DTO.TagListDto;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Tag;
import com.dawson.domain.vo.PageVo;
import com.dawson.domain.vo.TagVo;
import com.dawson.enums.AppHttpCodeEnum;
import com.dawson.exception.SystemException;
import com.dawson.mapper.TagMapper;
import com.dawson.service.TagService;
import com.dawson.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-10 19:40:26
 */

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {

        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName())
                .eq(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());


        Page page = new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);


        PageVo res = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(res);
    }

    @Override
    public ResponseResult addTag(TagDto tagDto) {

        Tag tag = new Tag();

        if( !StringUtils.hasText(tagDto.getName())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_NOT_NULL);
        } else if( !StringUtils.hasText(tagDto.getRemark())){
            throw new SystemException(AppHttpCodeEnum.TAGREMARK_NOT_NULL);
        }
        tag.setName(tagDto.getName());
        tag.setRemark(tagDto.getRemark());
        this.save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {

        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Tag::getId, id).set(Tag::getDelFlag, 1);
        this.update(updateWrapper);
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult showTag(Long id) {
        Tag tag = getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult alterTag(TagVo tagVo) {

        LambdaUpdateWrapper<Tag> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(Tag::getId, tagVo.getId())
                .set(Tag::getName, tagVo.getName())
                .set(Tag::getRemark, tagVo.getRemark());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {

        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getName);
        List<Tag> tags = list(queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyListBean(tags, TagVo.class);

        return ResponseResult.okResult(tagVos);
    }
}



