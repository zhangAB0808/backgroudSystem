package com.zhang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class SysMenu implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String path;

    /**
     * 
     */
    private String icon;

    /**
     * 
     */
    private String description;

    @TableField(exist = false)  //表示该字段在实体类有，但数据库没有
    private List<SysMenu> children;

    private Integer pid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}