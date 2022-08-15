package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weilai.server.pojo.Information;

import java.util.Map;

public interface InformationService extends IService<Information> {

    Map<String,Object> getInformation();

    Map<String,Object> getInformationById(Long eid);

    Boolean setInformation(Information information);
}
