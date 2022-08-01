package com.weilai.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weilai.server.pojo.Department;
import com.weilai.server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getPage/{pageNum}/{pageSize}")
    public Map<String,Object> getPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize){
        Page<Department> page = new Page<>(pageNum, pageSize);
        departmentService.page(page);
        HashMap<String, Object> map = new HashMap<>();
        map.put("pages",page.getPages());
        map.put("data",page.getRecords());
        return map;
    }

}
