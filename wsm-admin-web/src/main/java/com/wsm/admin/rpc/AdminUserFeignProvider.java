package com.wsm.admin.rpc;

import com.wsm.admin.feign.consumer.vo.UserVo;
import com.wsm.admin.model.User;
import com.wsm.admin.service.IUserService;
import com.wsm.common.util.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * admin user 服务提供者
 */
@RestController
public class AdminUserFeignProvider {

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/api/admin/user/{userName}")
    @ResponseBody
    public UserVo getByUserName(@PathVariable("userName") String userName) {
        UserVo userVo = null;
        User user = userService.findByUserName(userName);
        if (user != null){
            userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUserName(user.getUserName());
            userVo.setPassword(user.getPassword());
            userVo.setAvatar(user.getAvatar());
            userVo.setRealName(user.getRealName());
            userVo.setStatus(user.getStatus());
            userVo.setUpdateTime(user.getUpdateTime());
            userVo.setCreateTime(user.getCreateTime());
            userVo.setRecStatus(user.getRecStatus());
        }
        return userVo;
    }

    @GetMapping(value = "/api/admin/user/list")
    public AjaxJson findAll(){
        return AjaxJson.success(userService.findAll());
    }
}
