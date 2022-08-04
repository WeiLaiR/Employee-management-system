package com.weilai.server.utils;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private static long time = new Date().getTime();
//    计数位
    private static long count = 0;

    @Override
    public Long nextId(Object entity) {
        long id = new Date().getTime();
        System.out.println(id);
        System.out.println(time);
//      机器码,不会自动生成，所以用手设置。
        long mac = 66;

        if (id == time){
            count ++;
        }else {
            time = id;
            count = 0;
        }
//        乘100默认机器码两位十进制，可设置0-99
        id = id * 100 + mac;
//        乘10默认计数位一位十进制，可设置0-9
        id = id * 10 + count;
      	//返回生成的id值即可.
        return id;
    }
}
