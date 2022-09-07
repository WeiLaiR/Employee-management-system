package com.weilai.server_rsa.controller;


import com.weilai.server_rsa.utils.CreateRsaKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/RSA")
public class RsaController {
    private CreateRsaKey rsa;
    @Autowired
    public void setRsa(CreateRsaKey rsa) {
        this.rsa = rsa;
    }


    @PostMapping("/getPublicKey")
    public Map<String,Object> getPublicKey(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("publicKey", rsa.getPublicKey());
        return map;
    }

    @PostMapping("/getPrivateKey")
    public String getPrivateKey(){
        return rsa.getPrivateKey();
    }



}
