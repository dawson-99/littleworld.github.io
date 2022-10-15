package com.dawson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dawson.constant.SystemConstants;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Link;
import com.dawson.domain.vo.LinkVo;
import com.dawson.domain.vo.PageVo;
import com.dawson.mapper.LinkMapper;
import com.dawson.service.LinkService;
import com.dawson.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-10-05 22:18:33
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    /**
     * 查询所有审核通过的link
     * @return
     */
    @Override
    public ResponseResult getAllLink() {

        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> allLinkList = list(queryWrapper);

        List<LinkVo> linkVos = BeanCopyUtils.copyListBean(allLinkList, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult LinkList(Long pageNum, Long pageSize, String name, String status) {

        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status), Link::getStatus, status)
                .like(StringUtils.hasText(name), Link::getName, name);

        Page<Link> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}



