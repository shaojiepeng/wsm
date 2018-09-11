package com.wsm.sso.config;

import com.wsm.sso.filter.SSOFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SSOConfig {

    @Bean
    public FilterRegistrationBean xxlSsoFilterRegistration() {
        // filter
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setName("SSOFilter");
        registration.setOrder(1);
        registration.addUrlPatterns("/sso/*");
        registration.setFilter(new SSOFilter());
        registration.addInitParameter(Config.SSO_SERVER, "http://127.0.0.1:8080");

        return registration;
    }
}
