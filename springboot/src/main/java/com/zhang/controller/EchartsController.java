package com.zhang.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.util.ArrayUtil;
import com.zhang.common.Result;
import com.zhang.entity.SysUser;
import com.zhang.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private SysUserService  userService;

    @GetMapping("/members")
    public Result members(){
        List<SysUser> list = userService.list();
        int q1=0,q2=0,q3=0,q4=0; //四个季度
        for (SysUser user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1: q1+=1;break;
                case Q2: q2+=1;break;
                case Q3: q3+=1;break;
                case Q4: q4+=1;break;
                default:break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }

}
