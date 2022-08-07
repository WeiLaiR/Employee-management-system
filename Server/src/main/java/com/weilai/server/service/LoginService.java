package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weilai.server.pojo.Login;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface LoginService extends IService<Login> {

    Boolean loginEmp(String email,String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
