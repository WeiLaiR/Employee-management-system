package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weilai.server.pojo.Clockin;

import java.util.Map;

public interface ClockInService extends IService<Clockin> {
    Boolean setClockIn();

    Map<String,Object> getNowClockInPage(Integer pageNum,Integer pageSize,Integer day);
}
