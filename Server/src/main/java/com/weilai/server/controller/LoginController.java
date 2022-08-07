package com.weilai.server.controller;


import com.weilai.server.pojo.Login;
import com.weilai.server.pojo.LoginEnc;
import com.weilai.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

//    @RequestBody 用于接受前端传过来的json数据

    @GetMapping("/list")
    public List<Login> LoginList(){
        return loginService.list();
    }

    @PostMapping("/save")
    public boolean LoginSave(@RequestBody Login login){
        return loginService.save(login);
    }

    @PostMapping("/login")
    public Map<String,Object> LoginEmp(@RequestBody LoginEnc loginEnc) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return loginService.loginEmp(loginEnc.getEmail(), loginEnc.getEncryptPW());
    }


}
