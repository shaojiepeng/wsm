package com.wsm.operation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication(scanBasePackages={"com.wsm.common", "com.wsm.operation"})
@EnableAutoConfiguration
@EnableFeignClients(basePackages={"com.wsm.sso.feign.consumer", "com.wsm.admin.feign.consumer"})
@EnableEurekaClient
@EnableCaching
public class WsmOperationApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WsmOperationApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WsmOperationApplication.class, args);
    }
}
