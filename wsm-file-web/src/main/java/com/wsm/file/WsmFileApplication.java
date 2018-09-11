package com.wsm.file;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import(FdfsClientConfig.class)
@SpringBootApplication(scanBasePackages={"com.wsm.common", "com.wsm.file"})
@EnableAutoConfiguration
@EnableFeignClients(basePackages={"com.wsm.sso.feign.consumer", "com.wsm.admin.feign.consumer"})
@EnableEurekaClient
@EnableCaching
public class WsmFileApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WsmFileApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WsmFileApplication.class, args);
    }
}
