package com.weilai.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weilai.server.mapper.DepartmentMapper;
import com.weilai.server.mapper.LoginMapper;
import com.weilai.server.pojo.Department;
import com.weilai.server.pojo.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.management.Query;

@Service
public class AsyncService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Async
    public void createData(String email) {
        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Login login = loginMapper.selectOne(wrapper);
        if (login == null){
            return;
        }
        Department department = new Department();
        department.setEid(login.getEid());
        departmentMapper.insert(department);



    }





}
