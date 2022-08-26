package com.weilai.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilai.server.exception.CustomException;
import com.weilai.server.mapper.ClockInMapper;
import com.weilai.server.pojo.Clockin;
import com.weilai.server.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClockInServiceImpl extends ServiceImpl<ClockInMapper, Clockin> implements ClockInService {

    private ClockInMapper clockInMapper;
    @Autowired
    public void setClockInMapper(ClockInMapper clockInMapper) {
        this.clockInMapper = clockInMapper;
    }

    @Override
    public Boolean setClockIn() {
        Clockin clockin = new Clockin();
        Long id = TokenUtils.getId();
        QueryWrapper<Clockin> wrapper = new QueryWrapper<>();
        Date date1 = new Date();
        long temp = date1.getTime() % 86400000;
        long temp1 = date1.getTime() - temp - (3600000 * 8);
        date1 = new Date(temp1);
        wrapper.eq("eid",id)
                        .ge("clock_in_time",date1);
        List<Clockin> list = clockInMapper.selectList(wrapper);
        if (list.size() > 0) {
            throw new CustomException("Error","今日已打卡！");
        }

        clockin.setEid(id);
        return clockInMapper.insert(clockin) > 0;
    }

    /**
     * 用于获取今日已打卡人信息。若要获取未打卡人员信息，可与其他表对照通过二分查找找到不存在此数据中的员工id(因为id有序)。或在数据库中存储数据
     * @param pageNum 页数
     * @param pageSize 页面大小
     * @return 今日已打卡人信息
     */

    @Override
    public Map<String, Object> getNowClockInPage(Integer pageNum, Integer pageSize, Integer day) {
        HashMap<String,Object> map = new HashMap<>();
        QueryWrapper<Clockin> wrapper = new QueryWrapper<>();
        Date date1 = new Date();
        long temp = date1.getTime() % 86400000;
        long temp1 = date1.getTime() - temp - (3600000L * 8) - (day - 1) * 86400000L;
//        long temp2 = date1.getTime() - temp + (3600000L * 16 - 1);
        date1 = new Date(temp1);
//        Date date2 = new Date(temp2);
        wrapper.ge("clock_in_time",date1);
//                .le("clock_in_time",date2);
        Page<Clockin> page = new Page<>(pageNum, pageSize);
        clockInMapper.selectPage(page,wrapper);
        map.put("total",page.getTotal());
        map.put("values",page.getRecords());
        map.put("size",page.getRecords().size());
        return map;
    }

}
