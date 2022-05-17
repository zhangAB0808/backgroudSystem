package com.zhang.service;

import com.zhang.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findMenus(String name);



}
