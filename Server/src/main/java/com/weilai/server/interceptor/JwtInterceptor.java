package com.weilai.server.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.weilai.server.exception.CustomException;
import com.weilai.server.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

//        不是映射到方法，直接通过
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

//      获取token
        String token = request.getHeader("token");

//      校验token是否能正常解析
        String eid;
        try {
            eid = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException jwtDecodeException){
            throw new CustomException("Error","信息异常，请重新登录！");
        }

//      是否在redis中有记录
        String storedToken = redisUtil.get(eid);
        if (!StringUtils.hasText(storedToken)) {
            throw new CustomException("Error","信息异常，请重新登录！");
        }

//      对比token是否一致
        if (!storedToken.equals(token)) {
            throw new CustomException("Error","信息异常，请重新登录！");
        }

        return true;
    }
}
