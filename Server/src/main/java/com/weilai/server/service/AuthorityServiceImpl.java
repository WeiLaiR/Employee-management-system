package com.weilai.server.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weilai.server.exception.CustomException;
import com.weilai.server.mapper.AuthorityMapper;
import com.weilai.server.pojo.Authority;
import com.weilai.server.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    private AuthorityMapper authorityMapper;
    @Autowired
    public void setAuthorityMapper(AuthorityMapper authorityMapper) {
        this.authorityMapper = authorityMapper;
    }


    @Override
    public Map<String, Long> getAuthorityById(Long eid) {
        return getAuValue(eid);
    }

    @Override
    public Map<String, Long> getAuthority() {
        Long eid = TokenUtils.getId();
        return getAuValue(eid);
    }

    @NotNull
    private Map<String, Long> getAuValue(Long eid) {
        HashMap<String, Long> map = new HashMap<>();
        Authority authority = authorityMapper.selectById(eid);
        if (authority == null) {
            throw new CustomException("Error","权限信息获取失败！");
        }
        map.put("authority",authority.getAuthority());
        return map;
    }
}
