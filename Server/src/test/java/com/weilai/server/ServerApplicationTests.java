package com.weilai.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilai.server.mapper.LoginMapper;
import com.weilai.server.pojo.Department;
import com.weilai.server.pojo.Login;
import com.weilai.server.service.DepartmentService;
import com.weilai.server.service.LoginService;
import com.weilai.server.service.LoginServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    LoginMapper loginMapper;
    @Autowired
    private LoginService loginService;

    @Autowired
    private DepartmentService departmentService;

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
        login.setLevel(2);
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
        for (int i = 0; i < 88; i++) {
            Department department = new Department();
            department.setName("王八");
            department.setDepartment("开发部");
            department.setPost("后端开发工程师");
            departmentService.save(department);
        }
    }


}
