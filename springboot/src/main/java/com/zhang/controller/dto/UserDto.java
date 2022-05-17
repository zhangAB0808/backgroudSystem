package com.zhang.controller.dto;

import com.zhang.entity.SysMenu;
import lombok.Data;

import java.util.List;

/**
 * 接收前端登录·的参数
 *
 */
@Data
public class UserDto {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private Integer roleId;
    private List<SysMenu> menus;
}
