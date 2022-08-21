package com.weilai.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilai.server.mapper.LoginMapper;
import com.weilai.server.pojo.Department;
import com.weilai.server.pojo.Information;
import com.weilai.server.pojo.Login;
import com.weilai.server.service.DepartmentService;
import com.weilai.server.service.InformationService;
import com.weilai.server.service.LoginService;
import com.weilai.server.utils.RSAUtils;
import com.weilai.server.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.*;

import static com.weilai.server.utils.AesEncryptUtils.decrypt;
import static com.weilai.server.utils.AesEncryptUtils.encrypt;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    LoginMapper loginMapper;
    @Autowired
    private LoginService loginService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private InformationService informationService;

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
        login.setEmail("12345678@gmail.com");
        login.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        System.out.println(loginMapper.insert(login));
    }

//    逻辑删除测试成功
    @Test
    public void MybatisPlusTest03_delete() {
        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("email","123456@gmail.com");
        loginMapper.delete(wrapper);
    }

    @Test
    public void MybatisPlusTest04_query() {
        List<Login> list = loginService.list();
        for (Login login : list){
            System.out.println(login);
        }
    }

    @Test
    public void MybatisPlusTest05_save() {
        Login login = new Login();
        login.setEmail("1@gmail.com");
        login.setPassword("123456");
        loginService.save(login);
    }

    @Test
    public void MybatisPlusTest06_query() {
        List<Department> list = departmentService.list();
        for (Department department : list){
            System.out.println(department);
        }
    }

//  分页查询
    @Test
    public void MybatisPlusTest07_page() {
        Page<Department> page = new Page<>(1, 2);
        departmentService.page(page);
//        显示当前这一页的内容
        for (Department department : page.getRecords()){
            System.out.println(department);
        }
//        一共有多少页
        System.out.println(page.getPages());
    }

//  快速插入数据
    @Test
    public void MybatisPlusTest08_save() {
        for (int i = 0; i < 36; i++) {
            Department department = new Department();
            department.setName("马" + i);
            department.setDepartment("采购部");
            department.setPost("采购");
            departmentService.save(department);
        }
    }

    @Test
    public void InformationTest01() {
        Information information = new Information();
        information.setEid(1234567L);
        information.setBriefIntroduction("我最帅");
        information.setNickName("大帅逼");
        information.setPhoneNumber("139红酒白酒葡萄酒");
        information.setSex(true);
        information.setAge((short) 20);
        System.out.println(informationService.save(information));
    }

    @Test
    public void InformationTest02() {
        System.out.println(informationService.getById(1234567L));
    }



    @Test
    public void AesTest() throws Exception {
        Map<String,String> map=new HashMap<>();
        map.put("key","value");
        map.put("中文","汉字");

        System.out.println("加密前：" + map);

        String encrypt = encrypt(map.toString());
        System.out.println("加密后：" + encrypt);

        String decrypt = decrypt(encrypt);
        System.out.println("解密后：" + decrypt);
    }

    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void redisTest() {
        System.out.println(redisUtil.get("1659944841818660"));
        System.out.println(redisUtil.get("1659944841818660PW"));
    }


//    事实证明sha3_256Hex不会比MD5慢很多(4倍速度差？)
    @Test
    public void digest() {
        long time1 = new Date().getTime();
        for (int i = 0; i < 10000; i++) {
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11213ASVBBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdAH1234ET34TG3RGFJKdasfhvnaernBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdmlanglndasflkbnvalXSDFGevnasdiofSNLNKLaANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11213ASBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdVBAashoduhv893qhljH1234ET34TGsdhfvBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd809q34rg3RGFJKHGFSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("112BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd13ASVBAashoduhv893qhljH1234ET34BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdTGsdhfv809q34rg3RGFJW45KSNLdfb34yghNKLasdANLWNDBAKJS1415asd161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("112BBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd13ASVBAashoduhv893qhljH1234ET34TGsdhfv8BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd09q34rg3RGFJKsdfgSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("1121BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd3ASVBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdANLWNDASDFBABAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234ET34BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdTGsdhfv809q34rg3RGQWER5GWERDHFJKSNLdfb34yghNKLasdANLASDVAWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("112BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd13ASVBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3ASDFsdfvbsRGFJKSNLdBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFASDFJKSNLdfb34yghNKLasdANLWNDBAKJS1BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234ETBAashoduhv893qhljHBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd34TGsdhfv8sdfvb09q34ASDVAWERrg3RGFJKSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11213ASVBAashoduhv893qhljH1234ET34BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdTGsdhfvbsdcvb809q34ASeDVAWERrg3RGFJKSNLdfbBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234ETBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd34TGsdhfvsdfg809q34ASDVAWERrg3RGFJKSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234ETBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd34TGsdhfvsdfg809q34ASDVAWERrg3RGFJKSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234ETBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd34TGsdhfvsdfg809q34ASDVAWERrg3RGFJKSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdb34yghNKLasd213ASVBAashoduhv893qhljH1234ETBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd34TGsdhfvsdfg809q34ASDVAWERrg3RGFJKSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljHz123BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd4ET34TGsdhfvsdfg809q34ASDVAWERrfg3RGFJKSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11213ASVBAashoduhv893qhljH1234sBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdET34TGsdhfvsdfg809q34ASDVAWERrg3RvGFJKSNLdfb34yBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234fET34TGsdhfvsdfg809qr34ASDVAWERrg3RGFJKSNLdfb3BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd4yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH12BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd34ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234fET34TGsdhfvsdfg809qr34ASDVAWERrg3RGFJKSNLdfb3BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd4yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234fET34TGsdhfvsdfg809qr34ASDVAWERrg3RGFJKSNLdfb3BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd4yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGBAashoduhv893qhlBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdjH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234fET34TGsdhfvsdfg809qr34ASDVAWERrg3RGFJKSNLdfb3BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd4yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11213ASVBAashoduhv893qhljH1234BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdET34TGsdhfsdfgv809q34ASDBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdVAWERrg3RGFJKSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH1234EBAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasdT34TGsdhsdbfv809q34ASDVAWERrg3RGFJKSNLdfb34yghNKLasdANLWNDBAKJS1415161");
            org.apache.commons.codec.digest.DigestUtils.sha3_256Hex("11BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd213ASVBAashoduhv893qhljH123BAashoduhv893qhljH1234ET34TGsdhfv809q34rg3RGFJKdfgSNLdfb34yghNKLasd4ET34TGsdhsdfgfv809q34ASDVAWERrg3RGFJKSNLdfb34yghNKLasdANLWNDBAKJS1415161");
        }
        long time2 = new Date().getTime();
        System.out.println(time2 - time1);
        System.out.println(time1);
        System.out.println(time2);
    }


//    本机测试生成1000次大概十秒
//    那么不科学的预计的话，生成一次大概10ms，速度还行
    @Test
    public void rsaSpeedTest() {
        long time = new Date().getTime();
        for (int i = 0; i < 1000; i++) {
            Map<String, String> keys = RSAUtils.createKeys(1024);
        }
        long time2 = new Date().getTime();
        System.out.println(time2 - time);
    }

}
