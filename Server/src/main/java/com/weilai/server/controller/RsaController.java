package com.weilai.server.controller;

import com.weilai.server.utils.RSA;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/RSA")
public class RsaController {

    @PostMapping("/getPublicKey")
    public Map<String,Object> getPublicKey(){
        HashMap<String, Object> map = new HashMap<>();
        RSA rsa = new RSA();
        map.put("publicKey", rsa.publicKey);
        return map;
    }

}
