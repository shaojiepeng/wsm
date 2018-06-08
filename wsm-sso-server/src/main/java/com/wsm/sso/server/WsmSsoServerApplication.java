package com.wsm.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableFeignClients(basePackages={"com.wsm.admin.feign.consumer"})
@EnableEurekaClient
@SpringBootApplication(scanBasePackages={"com.wsm.sso.server"})
public class WsmSsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsmSsoServerApplication.class, args);
    }
}
