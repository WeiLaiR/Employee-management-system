package com.weilai.server_rsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //开启定时任务
@SpringBootApplication
public class ServerRsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerRsaApplication.class, args);
    }

}
