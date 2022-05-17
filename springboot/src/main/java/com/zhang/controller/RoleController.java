package com.zhang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.Result;
import com.zhang.entity.SysRole;
import com.zhang.entity.SysUser;
import com.zhang.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private SysRoleService roleService;

    @PostMapping("/save")
    public boolean save(@RequestBody SysRole role) {
        return roleService.saveOrUpdate(role);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return roleService.removeById(id);
    }

    @PostMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return roleService.removeByIds(ids);
    }

    @GetMapping
    public List<SysRole> findAll(){
        return roleService.list();
    }

    @GetMapping("/id")
    public Result findOne(@PathVariable Integer id){
        return Result.success(roleService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(Integer pageNum, Integer pageSize,String name) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.like("name",name);
        IPage<SysRole> iPage = roleService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<SysRole> records = iPage.getRecords();
        long total = iPage.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        return Result.success(map);
    }

    /**
     * 绑定角色和菜单的关系
     * @param roleId  角色id
     * @param menuIds 菜单id数组
     * @return
     */

    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId,menuIds);
        return Result.success();
    }

    @GetMapping("/getRoleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        return Result.success( roleService.getRoleMenu(roleId));
    }
}
