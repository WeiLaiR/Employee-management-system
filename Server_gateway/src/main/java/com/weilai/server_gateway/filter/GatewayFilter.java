package com.weilai.server_gateway.filter;

import com.google.common.base.Splitter;
import com.weilai.server_gateway.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;


@Order(1)
@Component
public class GatewayFilter implements GlobalFilter {

    @Value("${filter.config.excludeUrls}")
    private String excludeUrls; // 获取配置文件中不需要过滤的uri

    private List<String> excludes;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 初始化
        // 移除配置文件中不过滤url，字符串的前空白和尾部空白
        excludes = Splitter.on(",").trimResults().splitToList(this.excludeUrls);


        ServerHttpRequest request = exchange.getRequest();
        String uri = String.valueOf(request.getURI());

        if (isExcludesUrl(uri)){
            return chain.filter(exchange);
        }

        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("token");
        if (!StringUtils.hasText(token)) {
            throw new CustomException("Error", "无有效信息，请重新登录！");
        }

        return chain.filter(exchange);
    }



    private boolean isExcludesUrl(String path) {
        for (String v : this.excludes) {
            if (path.contains(v)) {// 判断请求uri 是否满足配置文件uri要求
                return true;  // 满足、也就是请求uri 为 登录、注册，返回true
            }
        }
        return false; // 不满足、也就是请求uri 不是登录、注册，返回false
    }
}
