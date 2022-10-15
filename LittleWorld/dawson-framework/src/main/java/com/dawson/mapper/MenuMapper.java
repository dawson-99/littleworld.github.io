package com.dawson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dawson.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-12 09:46:41
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectByPermsUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectMenuTreeById(Long userId);

    List<Long> selectMenuIdByRoleId(Long roleId);
}

