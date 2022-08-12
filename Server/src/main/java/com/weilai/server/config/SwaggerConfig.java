package com.weilai.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Swagger配置类
 */
@Controller
@EnableSwagger2
public class SwaggerConfig {

//    配置了Swagger的Docket的bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("WeiLai")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
//                RequestHandlerSelectors, //配置要扫描的接口方式
//                .any(), //扫描全部
//                .basePackage("com.weilai.server.**"), //指定要扫描的包
//                .none(), //不扫描
//                .withClassAnnotation(), //扫描类上的注解，参数是一个注解反射的对象
//                .withMethodAnnotation(), //扫描方法上的注解
//                .paths()
//               .paths(/weilai/**) //过滤什么路径
                .build();
    }


//    可配置多个分组
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("A");
    }



//   配置Swagger信息
    private ApiInfo apiInfo(){
        Contact contact = new Contact("WeiLai","https://baiblog.top/","aweilai@proton.me");

        return new ApiInfo(
                "WeiLai's Swagger",
                "Swagger",
                "v1.0",
                "https://baiblog.top",
                contact,
                "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }

}
