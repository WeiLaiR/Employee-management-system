package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weilai.server.pojo.Department;

import java.util.Map;

public interface DepartmentService extends IService<Department> {

    boolean addOrUpdateDep(Department department);

    Map<String,Object> getPage(Integer pageNum,Integer pageSize,String name,String dep);

    Map<String,Object> getDepById(Long eid);

    Map<String,Object> getDep();
}
