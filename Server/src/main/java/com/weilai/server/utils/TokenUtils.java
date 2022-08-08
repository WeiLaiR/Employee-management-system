package com.weilai.server.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class TokenUtils {

    /**
     * 生成Token
     */
    public static String getToken(Long eid,String passWord) {
//        id作为载荷
        return JWT.create().withAudience(eid.toString())
//                设置有效时间2h
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))
//                将通过HMAC-SHA256加密的用户密码作为密钥,所以这部分相较来说是安全的
                .sign(Algorithm.HMAC256(passWord));
    }
}
