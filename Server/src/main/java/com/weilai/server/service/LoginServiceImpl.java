package com.weilai.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilai.server.client.RsaClient;
import com.weilai.server.exception.CustomException;
import com.weilai.server.mapper.AuthorityMapper;
import com.weilai.server.mapper.LoginMapper;
import com.weilai.server.pojo.Authority;
import com.weilai.server.pojo.Login;
import com.weilai.server.utils.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements LoginService {

//    设置token过期时间，单位为秒，当前为3天
    private static final long TIMEOUT = 259200;

    //    更改为使用set方法注入
    private LoginMapper loginMapper;
    @Autowired
    public void setLoginMapper(LoginMapper loginMapper){
        this.loginMapper = loginMapper;
    }

    private AuthorityMapper authorityMapper;
    @Autowired
    public void setAuthorityMapper(AuthorityMapper authorityMapper) {
        this.authorityMapper = authorityMapper;
    }


    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private RsaClient rsaClient;
    @Autowired
    public void setRsaClient(RsaClient rsaClient) {
        this.rsaClient = rsaClient;
    }

    //    没有使用Bcrypt
//    使用的是SHA3-256

    @Override
    public Map<String, Object> loginEmp(String email, String password) throws Exception {
//      获取私钥，解密数据
        RSAPrivateKey privateKey = RSAUtils.getPrivateKey(AesEncryptUtils.decrypt(rsaClient.getPrivateKey()));
        String pw = RSAUtils.privateDecrypt(password, privateKey);
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Login emp = loginMapper.selectOne(wrapper);

        if (emp != null){
            if (DigestUtils.sha3_256Hex(pw).equals(emp.getPassword())){
                Authority authority = authorityMapper.selectById(emp.getEid());
                if (authority.getAuthority() == 0) {
                    throw new CustomException("Error","您无权登录！请联系管理员查明情况！");
                }

                String token = TokenUtils.getToken(emp.getEid(), emp.getEmail());
                redisUtil.set(emp.getEid().toString(), token,TIMEOUT);
                map.put("token",token);
                map.put("authority",authority.getAuthority());
                map.put("state","Success");
            }else {
                throw new CustomException("ErrorLogin","用户名或密码错误！");
            }
        }else {
            throw new CustomException("ErrorLogin","用户名或密码错误");
        }
        return map;
    }

    @Override
    public Map<String, Object> registerEmp(String email, String password) throws Exception {
        //      获取私钥，解密数据
        RSAPrivateKey privateKey = RSAUtils.getPrivateKey(AesEncryptUtils.decrypt(rsaClient.getPrivateKey()));
        String pw = RSAUtils.privateDecrypt(password, privateKey);

        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        HashMap<String, Object> map = new HashMap<>();
        if (loginMapper.selectOne(wrapper) != null){
            throw new CustomException("ErrorRegister", "该邮箱已被注册！");
        }else {
            Login login = new Login();
            login.setEmail(email);
            login.setPassword(DigestUtils.sha3_256Hex(pw));
            if (loginMapper.insert(login) > 0){
                map.put("state","Success");
            }else {
                throw new CustomException("ErrorRegister","注册失败，出现未知错误！");
            }
        }
        return map;
    }

    @Override
    public Boolean logout() {
        String eid = Objects.requireNonNull(TokenUtils.getId()).toString();

        if (!StringUtils.hasText(eid)) {
            throw new CustomException("Error","注销失败，出现未知错误！");
        }
        redisUtil.del(eid);
        System.out.println("===================================注销成功=================================");
        return true;
    }
}
