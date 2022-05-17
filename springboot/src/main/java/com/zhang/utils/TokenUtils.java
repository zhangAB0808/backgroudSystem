package com.zhang.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.zhang.entity.SysUser;
import com.zhang.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static SysUserService staticUserService;
    @Resource
    private SysUserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }


    /**
     * 生成token
     *
     * @param userId
     * @param sign
     * @return
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))//5小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录用户的信息
     *
     * @return
     */
    public static SysUser getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StringUtils.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.parseInt(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
