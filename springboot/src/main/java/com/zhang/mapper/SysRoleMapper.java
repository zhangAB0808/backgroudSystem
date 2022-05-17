package com.zhang.mapper;

import com.zhang.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Entity com.zhang.entity.SysRole
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select id from sys_role where name=#{roleName}")
    Integer getRoleIdByName(String roleName);
}




