package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilai.server.mapper.DepartmentMapper;
import com.weilai.server.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public boolean addOrUpdateDep(Department department) {
        if (department.getEid() == null){
            return departmentMapper.insert(department) != 0;
        }
        return departmentMapper.updateById(department) != 0;
    }
}
