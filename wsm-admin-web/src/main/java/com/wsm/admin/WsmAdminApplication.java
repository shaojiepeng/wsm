package com.wsm.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication(scanBasePackages={"com.wsm.common", "com.wsm.admin"})
@EnableAutoConfiguration
@EnableEurekaClient
@EnableFeignClients
@EnableCaching
public class WsmAdminApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WsmAdminApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WsmAdminApplication.class, args);
    }

}
