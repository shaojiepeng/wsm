package com.wsm.admin.feign.consumer.service;

import com.wsm.admin.feign.consumer.vo.UserVo;
import com.wsm.common.util.AjaxJson;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Admin user 服务消费者
 */
@FeignClient(value = "admin-service", path = "/api/admin")
public interface AdminUserFeignConsumer {

    @PostMapping(value = "/user/{userName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    UserVo getByUserName(@PathVariable("userName") String userName);

    @GetMapping("/user/list")
    AjaxJson findAll();
}
