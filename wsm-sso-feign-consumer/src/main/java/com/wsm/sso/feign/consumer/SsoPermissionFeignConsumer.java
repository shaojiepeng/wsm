package com.wsm.sso.feign.consumer;

import com.wsm.common.util.AjaxJson;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * sso permission 服务消费者
 */
@FeignClient(value = "sso-service", path = "/sso/api/permission")
public interface SsoPermissionFeignConsumer {

    @PostMapping(value = "/{resourceKey}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    AjaxJson hasPermission(@PathVariable("resourceKey") String resourceKey);
}
