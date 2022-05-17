package com.zhang.config.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.zhang.entity.SysUser;
import com.zhang.exception.ServiceException;
import com.zhang.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.zhang.common.Constants.CODE_401;

public class JwtInterceptor implements HandlerInterceptor{

    @Autowired
    private SysUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        if(StringUtils.isBlank(token)){
            throw new ServiceException(CODE_401,"无token,请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(CODE_401,"token验证失败,请重新登录");
        }
        SysUser user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(CODE_401,"用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            verifier.verify(token);
        }catch (JWTVerificationException e){
            throw new ServiceException(CODE_401,"token无效！请重新登录");
        }

        return true;

    }
    

}