package com.weilai.server.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.weilai.server.mapper")
public class MybatisPlusConfig {

}
