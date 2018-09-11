package com.wsm.admin.feign.consumer;

import com.wsm.admin.vo.UserVo;
import com.wsm.common.util.AjaxJson;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Admin user 服务消费者
 */
@FeignClient(value = "admin-service", path = "/admin/api/user")
public interface AdminUserFeignConsumer {

    @PostMapping(value = "/{userName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    UserVo getByUserName(@PathVariable("userName") String userName);

    @GetMapping(value = "/list}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    AjaxJson findAll();
}
