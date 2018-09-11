package com.wsm.common.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for (Cookie cookieTemp : cookies) {
                    String cookieIdentity = cookieTemp.getName();
                    //查找身份串
                    if (cookieIdentity.equals("sso_sessionid")) {
                        requestTemplate.header("Cookie", "sso_sessionid=" + cookieTemp.getValue());
                        break;
                    }
                }
            }
            /*String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
            if (!Strings.isNullOrEmpty(sessionId)) {
                requestTemplate.header("Cookie", "sso_sessionid=" + sessionId);
            }*/
        };
    }
}
