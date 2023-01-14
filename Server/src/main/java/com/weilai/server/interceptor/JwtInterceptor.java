package com.weilai.server.interceptor;

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

//        查看token是否为空
        if (!StringUtils.hasText(token)) {
            throw new CustomException("Error", "信息异常，请重新登录！");
        }

        return true;
    }
}
