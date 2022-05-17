package com.zhang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.common.Result;
import com.zhang.controller.dto.UserDto;
import com.zhang.entity.SysUser;

import java.util.Map;

/**
 *
 */
public interface SysUserService extends IService<SysUser> {

     Map<String, Object> findPage(Integer pageNum, Integer pageSize, String username, String email, String address);

    Result login(UserDto user);

    Result register(UserDto user);
}
