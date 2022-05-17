package com.zhang.mapper;

import com.zhang.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.zhang.entity.SysMenu
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

//    @Select("select id from sys_menu where path is null||path=''")
//    List<SysMenu> selectFatherId();
}




