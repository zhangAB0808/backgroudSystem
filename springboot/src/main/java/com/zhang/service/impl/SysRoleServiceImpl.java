package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.entity.RoleMenu;
import com.zhang.entity.SysMenu;
import com.zhang.entity.SysRole;
import com.zhang.mapper.RoleMenuMapper;
import com.zhang.mapper.SysMenuMapper;
import com.zhang.service.SysMenuService;
import com.zhang.service.SysRoleService;
import com.zhang.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private SysMenuService menuService;
    @Resource
    private SysMenuMapper menuMapper;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {

        //先删除当前角色id所有的绑定关系
        roleMenuMapper.deleteByRoleId(roleId);
        //再把前端传来的菜单id数组绑定到角色id中
        for (Integer menuId : menuIds) {
            SysMenu menu = menuService.getById(menuId);
            if(menu.getPid()!=null && !menuIds.contains(menu.getPid())){  //menu若为二级菜单,
                RoleMenu roleMenu = new RoleMenu();       // 并且传过来的menuIds没有它的父级id,那就补上这个父级id
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());        //多传一个角色id对应的父级id
                roleMenuMapper.insert(roleMenu);
            }
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //找出父级菜单id，path为空，有子菜单，传数据时舍去
        List<Integer> list = new ArrayList<>();
        for (Integer menuId : menuIds) {
            if(menuId!=11){
                list.add(menuId);
            }
        }
        return list;
    }
}




