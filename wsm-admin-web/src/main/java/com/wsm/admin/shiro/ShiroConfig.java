package com.wsm.admin.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.wsm.admin.service.IResourceService;
import com.wsm.common.shiro.ShiroManager;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Import(ShiroManager.class)
public class ShiroConfig {

    @Resource
    private IResourceService resourceService;

    @Bean(name = "myShiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public MyShiroRealm myShiroRealm() {
        return new MyShiroRealm();
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 用户授权信息Cache
     */
    @Bean(name = "shiroCacheManager")
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间,默认30天 ,单位秒：60 * 60 * 24 * 30
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }


    @Bean(name = "securityManager")
    @ConditionalOnMissingBean
    public DefaultSecurityManager securityManager() {
        DefaultSecurityManager sm = new DefaultWebSecurityManager();
        sm.setRealm(myShiroRealm());
        sm.setCacheManager(cacheManager());
        //注入记住我管理器
        sm.setRememberMeManager(rememberMeManager());
        return sm;
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
