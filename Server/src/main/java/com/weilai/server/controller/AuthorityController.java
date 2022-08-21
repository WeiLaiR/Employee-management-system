package com.weilai.server.controller;

import com.weilai.server.pojo.Authority;
import com.weilai.server.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    private AuthorityService authorityService;
    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping("/update")
    public Boolean updateAuthority(@RequestBody Authority authority) {
        return authorityService.updateById(authority);
    }

    @PostMapping("/getById")
    public Map<String,Long> getAuthorityById(@RequestBody Long eid) {
        return authorityService.getAuthorityById(eid);
    }

    @PostMapping("/get")
    public Map<String,Long> getAuthority() {
        return authorityService.getAuthority();
    }
}
