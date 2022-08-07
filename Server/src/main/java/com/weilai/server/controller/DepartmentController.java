package com.weilai.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weilai.server.pojo.Department;
import com.weilai.server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

//    分页条件查询
    @GetMapping("/getPage/{pageNum}/{pageSize}/{name}/{department}")
    public Map<String, Object> getPage(@PathVariable("pageNum") Integer pageNum,
                                          @PathVariable("pageSize") Integer pageSize,
                                          @PathVariable("name") String name,
                                          @PathVariable("department") String dep){
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        if (!name.equals("null")){
            wrapper.like("name",name);
        }
        if (!dep.equals("null")){
            wrapper.eq("department",dep);
        }
        Page<Department> page = new Page<>(pageNum, pageSize);
        departmentService.page(page,wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",page.getTotal());
        map.put("values",page.getRecords());
        map.put("size",page.getRecords().size());
        return map;
    }

    @PostMapping("/getById")
    public Map<String,Object> getById(@RequestBody Long eid) {
        HashMap<String, Object> map = new HashMap<>();
        Department department = departmentService.getById(eid);
        if (department != null){
            map.put("name",department.getName());
        }
        return map;
    }

//    当前用于手动添加或更新员工信息，后期功能会调整
    @PostMapping("/addDep")
    public boolean addDep(@RequestBody Department department){
        return departmentService.addOrUpdateDep(department);
    }


    @PostMapping("/deleteDep")
    public boolean deleteDep(@RequestBody Long eid){
        return departmentService.removeById(eid);
    }

    @PostMapping("/deleteDeps")
    public boolean deleteDeps(@RequestBody List<Department> list){
        return departmentService.removeByIds(list);
    }







}
