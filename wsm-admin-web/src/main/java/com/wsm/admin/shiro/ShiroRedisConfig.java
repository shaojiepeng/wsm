package com.wsm.admin.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.wsm.admin.redis.CustomRedisCacheManager;
import com.wsm.admin.redis.CustomRedisSessionDao;
import com.wsm.admin.service.IResourceService;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@Configuration
//@Import(ShiroManager.class)
public class ShiroRedisConfig {

    @Resource
    private IResourceService resourceService;

    @Bean(name = "myShiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm realm = new MyShiroRealm();
        realm.setCacheManager(redisCacheManager());
        return realm;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 用户授权信息Cache
     */
    /*@Bean(name = "shiroCacheManager")
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }*/

    /*@Bean(name = "securityManager")
    @ConditionalOnMissingBean
    public DefaultSecurityManager securityManager() {
        DefaultSecurityManager sm = new DefaultWebSecurityManager();
        sm.setRealm(myShiroRealm());
        sm.setCacheManager(cacheManager());
        return sm;
    }*/

    @Bean
    public CustomRedisCacheManager redisCacheManager() {
        return new CustomRedisCacheManager();
    }

    @Bean(name = "redisSessionDAO")
    public CustomRedisSessionDao sessionDAO() {
        return new CustomRedisSessionDao();
    }

    /**
     * SessionManager session管理
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setGlobalSessionTimeout(1800);
        sessionManager.setCacheManager(redisCacheManager());
        return sessionManager;
    }

    /**
     * SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() throws Exception {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/admin/login");
        //登录成功后要跳转的链接
        shiroFilter.setSuccessUrl("/admin/index");
        //未授权界面
        shiroFilter.setUnauthorizedUrl("/previlige/no");
        
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置不会被拦截的链接 顺序判断
        //静态资源不拦截
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        //登录链接不拦截
        filterChainDefinitionMap.put("/admin/login", "anon");
        filterChainDefinitionMap.put("/admin", "anon");
        filterChainDefinitionMap.put("/kaptcha", "anon");
        
        /*Map<String, Filter> filters = shiroFilter.getFilters();
        filters.put("authc", new CustomFormAuthenticationFilter());*/

        List<com.wsm.admin.model.Resource> list = resourceService.findAll();
        for (com.wsm.admin.model.Resource resource : list) {
            filterChainDefinitionMap.put(resource.getUrl(), "perms[" + resource.getResourceKey() + "]");
        }

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilter;
    }
}
