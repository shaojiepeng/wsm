package com.wsm.sso.server.config;

import com.wsm.sso.util.JedisUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *  2018-04-03 20:41:07
 */
@Configuration
public class SSORedisConfig implements InitializingBean {

    @Value("${redis.address}")
    private String redisAddress;

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisUtil.init(redisAddress);
    }

}
