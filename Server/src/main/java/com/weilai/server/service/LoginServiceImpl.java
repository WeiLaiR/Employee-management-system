package com.weilai.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilai.server.mapper.LoginMapper;
import com.weilai.server.pojo.Login;
import com.weilai.server.utils.RSA;
import com.weilai.server.utils.RSAUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    RSA rsa = new RSA();

    @Override
    public Map<String, Object> loginEmp(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
//      获取私钥，解密数据
        RSAPrivateKey privateKey = RSAUtils.getPrivateKey(rsa.privateKey);
        String pw = RSAUtils.privateDecrypt(password, privateKey);

        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Login emp = loginMapper.selectOne(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        if (emp != null){
            if (pw.equals(emp.getPassword())){
                map.put("state","Success");
                map.put("eid",emp.getEid());
                map.put("level",emp.getLevel());
            }else {
                map.put("state","Error");
            }
        }else {
            map.put("state","Error");
        }
        return map;
    }
}
