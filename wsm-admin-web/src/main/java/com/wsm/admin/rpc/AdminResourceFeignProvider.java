package com.wsm.admin.rpc;

import com.wsm.admin.dto.ResourceTree;
import com.wsm.admin.model.User;
import com.wsm.admin.service.IResourceService;
import com.wsm.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * admin role 服务提供者
 */
@RestController
public class AdminResourceFeignProvider {

    @Autowired
    private IResourceService resourceService;
    @Autowired
    private IUserService userService;

    @GetMapping(value = "/api/admin/resource/getResourcesByUser/{userName}/{resourceKey}")
    @ResponseBody
    public List<ResourceTree> getByUserName(@PathVariable("userName") String userName, @PathVariable("resourceKey") String resourceKey) throws Exception {
        User dbUser = userService.findByUserName(userName);
        if (dbUser != null){
            return resourceService.getResourcesByUser(dbUser, resourceKey);
        }else{
            return new ArrayList<>();

        }
    }
}
