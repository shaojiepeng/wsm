package com.wsm.admin.feign.consumer;

import com.wsm.admin.dto.ResourceTree;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Admin Resource 服务消费者
 */
@FeignClient(value = "admin-service", path = "/admin/api/resource")
public interface AdminResourceFeignConsumer {

    @GetMapping(value = "/getResourcesByUser/{userName}/{resourceKey}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    List<ResourceTree> getResourcesByUser(@RequestParam("userName") String userName, @RequestParam("resourceKey")String resourceKey);
}
