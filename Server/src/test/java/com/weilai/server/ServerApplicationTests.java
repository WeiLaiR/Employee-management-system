package com.weilai.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.weilai.server.mapper.LoginMapper;
import com.weilai.server.pojo.Department;
import com.weilai.server.pojo.Information;
import com.weilai.server.pojo.Login;
import com.weilai.server.service.ClockInService;
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

    private ClockInService clockInService;

    @Test
    void contextLoads() {
    }

    @Test
    public void dateSearch() throws JsonProcessingException {
        Date date = new Date();
        long temp = date.getTime() % 86400000;
        long temp1 = date.getTime() - temp - (3600000 * 8) - (5 * 86400000);
        long temp2 = date.getTime() - temp + (3600000 * 16 - 1) - (5 * 86400000);
        Date date1 = new Date(temp1);
        Date date2 = new Date(temp2);
        System.out.println(date1);
        System.out.println(date2);
        QueryWrapper<Information> wrapper = new QueryWrapper<>();
        wrapper.ge("create_time",date1)
                .le("create_time",date2);
        List<Information> list = informationService.list(wrapper);
        System.out.println(list);
        System.out.println(list.size());

    }

    @Test
    public void clockIn() {

    }


    @Test
    public void lgoTest() {
        Logger logger = LoggerFactory.getLogger(ServerApplicationTests.class);

        logger.error("error??????");
        logger.warn("warn??????");
        logger.info("info??????");
        logger.debug("debug??????");
        logger.trace("trace??????");

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

//    ????????????????????????
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

//  ????????????
    @Test
    public void MybatisPlusTest07_page() {
        Page<Department> page = new Page<>(1, 2);
        departmentService.page(page);
//        ??????????????????????????????
        for (Department department : page.getRecords()){
            System.out.println(department);
        }
//        ??????????????????
        System.out.println(page.getPages());
    }

//  ??????????????????
    @Test
    public void MybatisPlusTest08_save() {
        for (int i = 0; i < 7; i++) {
            Department department = new Department();
            department.setEid(1661316910846660L + i);
            department.setName("???" + i);
            department.setDepartment("?????????");
            department.setPost("????????????");
            departmentService.save(department);
        }
    }

    @Test
    public void InformationTest01() {
        Information information = new Information();
        information.setEid(1234567L);
        information.setBriefIntroduction("?????????");
        information.setNickName("?????????");
        information.setPhoneNumber("139?????????????????????");
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
        map.put("??????","??????");

        System.out.println("????????????" + map);

        String encrypt = encrypt(map.toString());
        System.out.println("????????????" + encrypt);

        String decrypt = decrypt(encrypt);
        System.out.println("????????????" + decrypt);
    }

    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void redisTest() {
        System.out.println(redisUtil.get("1659944841818660"));
        System.out.println(redisUtil.get("1659944841818660PW"));
    }


//    ????????????sha3_256Hex?????????MD5?????????(4???????????????)
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


//    ??????????????????1000???????????????
//    ???????????????????????????????????????????????????10ms???????????????
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
