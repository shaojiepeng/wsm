package com.wsm.file.config;

import com.wsm.sso.config.Config;
import com.wsm.sso.filter.SSOFilter;
import com.wsm.sso.util.JedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SSOConfig {

    @Value("${sso.server}")
    private String ssoServer;

    @Value("${sso.logout.path}")
    private String ssoLogoutPath;

    @Value("${sso.redis.address}")
    private String ssoRedisAddress;

    @Bean
    public FilterRegistrationBean xxlSsoFilterRegistration() {
        // redis init
        JedisUtil.init(ssoRedisAddress);
        // filter
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("SSOFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/file/*", "/logout");
        registration.setFilter(new SSOFilter());
        registration.addInitParameter(Config.SSO_SERVER, ssoServer);
        registration.addInitParameter(Config.SSO_LOGOUT_PATH, ssoLogoutPath);

        return registration;
    }
}
