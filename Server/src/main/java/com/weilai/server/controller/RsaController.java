package com.weilai.server.controller;

import com.weilai.server.utils.RSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/server/RSA")
public class RsaController {
    private RSA rsa;
    @Autowired
    public void setRsa(RSA rsa) {
        this.rsa = rsa;
    }

    @PostMapping("/getPublicKey")
    public Map<String,Object> getPublicKey(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("publicKey", rsa.getPublicKey());
        return map;
    }

}
