package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilai.server.mapper.DepartmentMapper;
import com.weilai.server.pojo.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
}
