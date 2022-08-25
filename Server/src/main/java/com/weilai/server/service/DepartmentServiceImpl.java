package com.weilai.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilai.server.mapper.DepartmentMapper;
import com.weilai.server.pojo.Department;
import com.weilai.server.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    //    更改为使用set方法注入
    private DepartmentMapper departmentMapper;
    @Autowired
    public void setDepartmentMapper(DepartmentMapper departmentMapper){
        this.departmentMapper = departmentMapper;
    }
    @Override
    public boolean addOrUpdateDep(Department department) {
        if (department.getEid() == null){
            return departmentMapper.insert(department) != 0;
        }
        return departmentMapper.updateById(department) != 0;
    }

    @Override
    public Map<String, Object> getPage(Integer pageNum, Integer pageSize, String name, String dep) {
        HashMap<String,Object> map = new HashMap<>();
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        if (!name.equals("null")){
            wrapper.like("name",name);
        }
        if (!dep.equals("null")){
            wrapper.eq("department",dep);
        }
        Page<Department> page = new Page<>(pageNum, pageSize);
        departmentMapper.selectPage(page,wrapper);
        map.put("total",page.getTotal());
        map.put("values",page.getRecords());
        map.put("size",page.getRecords().size());
        return map;
    }

    @Override
    public Map<String, Object> getDepById(Long eid) {
        return getDepMap(eid);
    }

    @Override
    public Map<String, Object> getDep() {
        Long eid = TokenUtils.getId();
        return getDepMap(eid);
    }

    @NotNull
    private Map<String, Object> getDepMap(Long eid) {
        HashMap<String, Object> map = new HashMap<>();
        Department department = departmentMapper.selectById(eid);
        if (department != null){
            map.put("name",department.getName());
            map.put("eid",department.getEid());
            map.put("department",department.getDepartment());
            map.put("post",department.getPost());
        }
        return map;
    }

}
