package com.weilai.server.controller;


import com.weilai.server.pojo.LoginEnc;
import com.weilai.server.service.AsyncService;
import com.weilai.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/server/login")
public class LoginController {

//    更改为使用set方法注入
    private LoginService loginService;
    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    private AsyncService asyncService;
    @Autowired
    public void setAsyncService(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

//    @RequestBody 用于接受前端传过来的json数据

    @PostMapping("/login")
    public Map<String,Object> LoginEmp(@RequestBody LoginEnc loginEnc) throws Exception {
        return loginService.loginEmp(loginEnc.getEmail(), loginEnc.getEncryptPW());
    }


    @PostMapping("/register")
    public Map<String,Object> RegisterEmp(@RequestBody LoginEnc loginEnc) throws Exception {
        Map<String,Object> map = loginService.registerEmp(loginEnc.getEmail(), loginEnc.getEncryptPW());
        asyncService.createData(loginEnc.getEmail());
        return map;
    }

    @PostMapping("/logout")
    public Boolean LogOut() {
        return loginService.logout();
    }

}
