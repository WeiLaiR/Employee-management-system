package com.weilai.server.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient("server-rsa")
public interface RsaClient {

    @PostMapping("/RSA/getPrivateKey")
    public String getPrivateKey();
}
