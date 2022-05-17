package com.zhang.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.entity.SysMenu;
import com.zhang.service.SysMenuService;
import com.zhang.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    @Override
    public List<SysMenu> findMenus(String name) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        //查询所有数据
        List<SysMenu> list = this.list(queryWrapper);
        //找出pid为空的一级菜单
        List<SysMenu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (SysMenu menu : parentNodes) {
            menu.setChildren(list.stream().filter(m->menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}




