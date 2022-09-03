package com.weilai.server.controller;

import com.weilai.server.annotation.AuthorityAnnotation;
import com.weilai.server.pojo.Department;
import com.weilai.server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/server/department")
public class DepartmentController {

    //    更改为使用set方法注入
    private DepartmentService departmentService;
    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

//    分页条件查询
    @AuthorityAnnotation(sign = 2)
    @GetMapping("/getPage/{pageNum}/{pageSize}/{name}/{department}")
    public Map<String, Object> getPage(@PathVariable("pageNum") Integer pageNum,
                                          @PathVariable("pageSize") Integer pageSize,
                                          @PathVariable("name") String name,
                                          @PathVariable("department") String dep){
        return departmentService.getPage(pageNum,pageSize,name,dep);
    }

    @AuthorityAnnotation(sign = 2)
    @PostMapping("/getById")
    public Map<String,Object> getById(@RequestBody Long eid) {
        return departmentService.getDepById(eid);
    }


    @AuthorityAnnotation(sign = 1)
    @PostMapping("/get")
    public Map<String,Object> get() {
        return departmentService.getDep();
    }

//    当前用于手动添加或更新员工信息，后期功能或许会调整(因为目前只用到更新，没用到添加)
    @PostMapping("/addDep")
    public boolean addDep(@RequestBody Department department){
        return departmentService.addOrUpdateDep(department);
    }


    @AuthorityAnnotation(sign = 2)
    @PostMapping("/deleteDep")
    public boolean deleteDep(@RequestBody Long eid){
        return departmentService.removeById(eid);
    }

    @AuthorityAnnotation(sign = 2)
    @PostMapping("/deleteDeps")
    public boolean deleteDeps(@RequestBody List<Department> list){
        return departmentService.removeByIds(list);
    }







}
