package com.weilai.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weilai.server.exception.CustomException;
import com.weilai.server.mapper.DepartmentMapper;
import com.weilai.server.mapper.InformationMapper;
import com.weilai.server.mapper.LoginMapper;
import com.weilai.server.pojo.Department;
import com.weilai.server.pojo.Information;
import com.weilai.server.pojo.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    private LoginMapper loginMapper;
    @Autowired
    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    private DepartmentMapper departmentMapper;
    @Autowired
    public void setDepartmentMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    private InformationMapper informationMapper;
    @Autowired
    public void setInformationMapper(InformationMapper informationMapper) {
        this.informationMapper = informationMapper;
    }

    @Async
    public void createData(String email) {
        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Login login = loginMapper.selectOne(wrapper);
        if (login == null){
            throw new CustomException("Error","数据初始化失败！");
        }
        boolean sign = false;
        Department department = new Department();
        department.setEid(login.getEid());
        if (departmentMapper.insert(department) < 1) {
            sign = true;
        }
        Information information = new Information();
        information.setEid(login.getEid());
        if (informationMapper.insert(information) < 1) {
            sign = true;
        }



        if (sign) {
            throw new CustomException("Error","部分数据初始化失败！");
        }
    }





}
