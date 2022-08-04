package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weilai.server.pojo.Department;

public interface DepartmentService extends IService<Department> {

    boolean addOrUpdateDep(Department department);
}
