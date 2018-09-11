package com.wsm.sso.rpc;

import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
import com.wsm.sso.config.Config;
import com.wsm.sso.model.SSOUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * sso permission 服务提供者
 */
@RestController
public class SsoPermissionFeignProvider extends BaseController {

    @PostMapping(value = "/sso/api/permission/{resourceKey}")
    AjaxJson hasPermission(@PathVariable("resourceKey") String resourceKey){
        boolean hasPermission = false;
        SSOUser ssoUser = (SSOUser) request.getAttribute(Config.SSO_USER);
        if (ssoUser != null && ssoUser.getPermissionSet() != null){
            Set<String> permissionSet = ssoUser.getPermissionSet();
            if (permissionSet.contains(resourceKey)){
                hasPermission = true;
            }
        }
        return AjaxJson.success(hasPermission);
    }

}
