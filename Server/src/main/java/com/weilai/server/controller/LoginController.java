package com.weilai.server.controller;


import com.weilai.server.pojo.Login;
import com.weilai.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
