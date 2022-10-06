package com.dawson.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dawson.domain.ResponseResult;
import com.dawson.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-10-05 22:18:32
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
