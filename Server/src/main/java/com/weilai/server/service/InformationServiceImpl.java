package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilai.server.mapper.InformationMapper;
import com.weilai.server.pojo.Information;
import com.weilai.server.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements InformationService {

    private InformationMapper informationMapper;
    @Autowired
    public void setInformationMapper(InformationMapper informationMapper) {
        this.informationMapper = informationMapper;
    }


    @Override
    public Map<String, Object> getInformation() {
        Long eid = TokenUtils.getId();
        return getInformationData(eid);
    }

    @Override
    public Map<String, Object> getInformationById(Long eid) {
        return getInformationData(eid);
    }

    @Override
    public Boolean setInformation(Information information) {
        return informationMapper.updateById(information) > 0;
    }

    @NotNull
    private Map<String, Object> getInformationData(Long eid) {
        HashMap<String, Object> map = new HashMap<>();
        Information information = informationMapper.selectById(eid);
        map.put("eid",information.getEid());
        map.put("sex",information.getSex());
        map.put("age",information.getAge());
        map.put("education",information.getEducation());
        map.put("phoneNumber",information.getPhoneNumber());
        map.put("nickName",information.getNickName());
        map.put("address",information.getAddress());
        map.put("hobby",information.getHobby());
        map.put("briefIntroduction",information.getBriefIntroduction());
        map.put("remarks",information.getRemarks());
        return map;
    }
}
