package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weilai.server.pojo.Authority;

import java.util.Map;

public interface AuthorityService extends IService<Authority> {

    Map<String,Long> getAuthorityById(Long eid);

    Map<String,Long> getAuthority();

}
