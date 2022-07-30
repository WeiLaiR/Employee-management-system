package com.weilai.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weilai.server.mapper.LoginMapper;
import com.weilai.server.pojo.Login;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    LoginMapper loginMapper;
    @Test
    void contextLoads() {
    }


    @Test
    public void lgoTest() {
        Logger logger = LoggerFactory.getLogger(ServerApplicationTests.class);

        logger.error("error信息");
        logger.warn("warn信息");
        logger.info("info信息");
        logger.debug("debug信息");
        logger.trace("trace信息");

    }

    @Test
    public void MybatisPlusTest01_query() {
        List<Login> list = loginMapper.selectList(null);
        for (Login login : list){
            System.out.println(login);
        }
    }

    @Test
    public void MybatisPlusTest02_insert() {
        Login login = new Login();
        login.setEmail("123456@gmail.com");
        login.setPassword("123456");
        login.setLevel(1);
        loginMapper.insert(login);
    }

//    逻辑删除测试成功
    @Test
    public void MybatisPlusTest03_delete() {
        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("email","123456@gmail.com");
        loginMapper.delete(wrapper);
    }


}
