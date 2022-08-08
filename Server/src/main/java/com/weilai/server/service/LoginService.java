package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weilai.server.pojo.Login;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public interface LoginService extends IService<Login> {

    Map<String,Object> loginEmp(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;


    Map<String,Object> registerEmp(String email,String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
