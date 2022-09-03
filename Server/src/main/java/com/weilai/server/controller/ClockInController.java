package com.weilai.server.controller;

import com.weilai.server.service.ClockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/server/clock")
public class ClockInController {

    private ClockInService clockInService;
    @Autowired
    public void setClockInService(ClockInService clockInService) {
        this.clockInService = clockInService;
    }

    @PostMapping("/set")
    public Boolean setClockIn() {
        return clockInService.setClockIn();
    }

    @GetMapping("/getNowPage/{pageNum}/{pageSize}/{day}")
    public Map<String,Object> getNowPage(@PathVariable("pageNum") Integer pageNum,
                                         @PathVariable("pageSize") Integer pageSize,
                                         @PathVariable("day") Integer day) {
        return clockInService.getNowClockInPage(pageNum,pageSize,day);
    }

}
