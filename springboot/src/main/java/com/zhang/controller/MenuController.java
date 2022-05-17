package com.zhang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.common.Result;
import com.zhang.entity.SysDict;
import com.zhang.entity.SysMenu;
import com.zhang.entity.SysRole;
import com.zhang.service.SysDictService;
import com.zhang.service.SysMenuService;
import com.zhang.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private SysMenuService menuService;

    @Resource
    private SysDictService dictService;

    @PostMapping("/save")
    public boolean save(@RequestBody SysMenu menu) {
        return menuService.saveOrUpdate(menu);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return menuService.removeById(id);
    }

    @PostMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return menuService.removeByIds(ids);
    }

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name){
        return Result.success(menuService.findMenus(name));
    }

    @GetMapping("/id")
    public Result findOne(@PathVariable Integer id){
        return Result.success(menuService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(Integer pageNum, Integer pageSize,String name) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        IPage<SysMenu> iPage = menuService.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<SysMenu> records = iPage.getRecords();
        long total = iPage.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        return Result.success(map);
    }

    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","icon");
        return Result.success(dictService.list(queryWrapper));
    }
}
