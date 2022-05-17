package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.common.Result;
import com.zhang.controller.dto.UserDto;
import com.zhang.entity.SysMenu;
import com.zhang.entity.SysUser;
import com.zhang.exception.ServiceException;
import com.zhang.mapper.RoleMenuMapper;
import com.zhang.mapper.SysRoleMapper;
import com.zhang.mapper.SysUserMapper;
import com.zhang.service.SysMenuService;
import com.zhang.service.SysUserService;
import com.zhang.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zhang.common.Constants.*;

/**
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private SysMenuService menuService;

    public Map<String, Object> findPage(Integer pageNum, Integer pageSize, String username, String email, String address) {
        IPage<SysUser> page = new Page<>(pageNum, pageSize);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }

        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        queryWrapper.orderByDesc("id");
        IPage<SysUser> iPage = this.page(page, queryWrapper);
        List<SysUser> records = iPage.getRecords();
        long total = iPage.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        return map;
    }

    @Override
    public Result login(UserDto user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            return Result.error(CODE_400, "参数错误");
        }
        //对密码进行MD5的加盐加密 验证
//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        SysUser userOne;
        try {
            userOne = userMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            throw new ServiceException(CODE_500, "系统错误");
        }

        if (userOne != null) {
            BeanUtils.copyProperties(userOne, user);
            String token = TokenUtils.genToken(userOne.getId().toString(),userOne.getPassword());
            user.setPassword("");
            user.setToken(token);
            String roleName = userOne.getRole();
            //查找设置角色对应的菜单列表
            List<SysMenu> roleMenus = getRoleMenus(roleName);
            user.setMenus(roleMenus);
            return Result.success(user);
        } else {
            throw new ServiceException(CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public Result register(UserDto user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            return Result.error(CODE_400, "参数错误");
        }
        //对用户注册的密码进行MD5的加盐加密 存入数据库更安全
//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        SysUser userOne = userMapper.selectOne(queryWrapper);
        if (userOne == null) {
            userOne = new SysUser();
           BeanUtils.copyProperties(user,userOne);
            if (this.save(userOne)) {
                return Result.success();
            } else {
                return Result.error();
            }
        } else {
            return Result.error(CODE_600, "用户已存在");
        }
    }


    /**
     * 获取当前角色的菜单列表
     * @param roleName
     * @return
     */
    private List<SysMenu> getRoleMenus(String roleName){
        //根据名字获取角色id
        Integer roleId = roleMapper.getRoleIdByName(roleName);
        //获得当前角色所有的菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查出系统所有菜单
        List<SysMenu> menus = menuService.findMenus("");

        ArrayList<SysMenu> roleMenus = new ArrayList<>();
        //筛选当前用户角色的菜单
        for (SysMenu menu : menus) {
            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<SysMenu> children = menu.getChildren();
            //移除children里面不在menuIds集合里面的子菜单
            children.removeIf(child->!menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

}




