package com.wsm.admin.rpc;

import com.wsm.admin.feign.consumer.service.AdminUserFeignConsumer;
import com.wsm.admin.feign.consumer.vo.UserVo;
import com.wsm.common.util.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin user rpc api, 调用admin server 的服务提供方接口
 */
@RestController
public class AdminUserFeignClientApi {

    @Autowired
    private AdminUserFeignConsumer consumer;

    @RequestMapping(value = "/api/admin/getByUserName/{userName}", method = RequestMethod.POST)
    public AjaxJson findByUserName(@PathVariable("userName") String userName) {
        UserVo userVo = consumer.getByUserName(userName);
        if (userVo != null){
            return AjaxJson.success(userVo);
        }
        return AjaxJson.failure("用户不存在。");
    }

    @RequestMapping(value = "/api/admin/findAll", method = RequestMethod.GET)
    public AjaxJson findAll() {
        return consumer.findAll();
    }

}
