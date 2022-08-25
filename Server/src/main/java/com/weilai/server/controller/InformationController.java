package com.weilai.server.controller;

import com.weilai.server.annotation.AuthorityAnnotation;
import com.weilai.server.pojo.Information;
import com.weilai.server.service.InformationService;
import com.weilai.server.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/information")
public class InformationController {

    private InformationService informationService;

    @Autowired
    public void setInformationService(InformationService informationService) {
        this.informationService = informationService;
    }

    @AuthorityAnnotation(sign = 3)
    @PostMapping("/getInformation")
    public Map<String,Object> getInformation() {
        return informationService.getInformation();
    }

    @AuthorityAnnotation(sign = 2)
    @PostMapping("/getInformationById")
    public Map<String,Object> getInformationById(@RequestBody Long eid) {
        return informationService.getInformationById(eid);
    }

    @AuthorityAnnotation(sign = 3)
    @PostMapping("/setInformation")
    public Boolean setInformation(@RequestBody Information information) {
        return informationService.setInformation(information);
    }





}
