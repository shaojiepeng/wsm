package com.wsm.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.wsm.admin")
@SpringBootApplication(scanBasePackages={"com.wsm.admin", "com.wsm.common"})
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
