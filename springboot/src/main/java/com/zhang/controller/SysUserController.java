package com.zhang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.common.Result;
import com.zhang.controller.dto.UserDto;
import com.zhang.entity.SysUser;
import com.zhang.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private SysUserService userService;

    @GetMapping("/")
    public List<SysUser> index() {
        return userService.list();
    }

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        SysUser one = userService.getOne(queryWrapper);
        return Result.success(one);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody SysUser user) {
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }

    @GetMapping("/page")
    public Map<String, Object> findPage(Integer pageNum, Integer pageSize, @RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String email, @RequestParam(defaultValue = "") String address) {
        return userService.findPage(pageNum, pageSize, username, email, address);
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDto user){
        return userService.login(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDto user){
        return userService.register(user);
    }
}
