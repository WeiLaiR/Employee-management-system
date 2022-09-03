package com.weilai.server.config;

import com.weilai.server.interceptor.AuthorityInterceptor;
import com.weilai.server.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
//               拦截所有请求并判断Token是否合法
                .addPathPatterns("/**")
                .excludePathPatterns("/server/login/login", "/server/login/register", "/server/RSA/getPublicKey")
//               设置拦截器优先级(越小越高)
                .order(0);
        registry.addInterceptor(authorityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/server/login/login", "/server/login/register", "/server/RSA/getPublicKey")
                .order(10);
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
    @Bean
    public AuthorityInterceptor authorityInterceptor() {
        return new AuthorityInterceptor();
    }
}
