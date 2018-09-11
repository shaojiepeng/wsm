package com.wsm.admin.api;

import com.wsm.admin.model.User;
import com.wsm.admin.service.IResourceService;
import com.wsm.admin.service.IUserService;
import com.wsm.common.api.BaseController;
import com.wsm.sso.config.Config;
import com.wsm.sso.model.SSOUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController{
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IResourceService resourceService;

    @Value("${sso.sys.path}")
    private String ssoSysPath;
    @Value("${url.admin.path}")
    private String adminSysPath;
    @Value("${url.member.path}")
    private String memberSysPath;
    @Value("${url.goods.path}")
    private String goodsSysPath;
    @Value("${url.trade.path}")
    private String tradeSysPath;
    @Value("${url.operation.path}")
    private String operationSysPath;
    @Value("${url.file.path}")
    private String fileSysPath;
    @Value("${url.setting.path}")
    private String settingSysPath;
    @Value("${url.statistics.path}")
    private String statisticsSysPath;

    /**
     * 控制台首页
     * @return
     */
    @RequestMapping("/admin/home")
    public String home(){
        return "home";
    }

    /**
     * 控制台
     * @param model
     * @return
     */
	@RequestMapping(value = {"/admin", "/admin/index"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model){
        getResourcesByUserAndResourceKey(request, model, "system");
		return "index";
	}

    private void getResourcesByUserAndResourceKey(HttpServletRequest request, Model model, String resourceKey) {
        SSOUser ssoUser = (SSOUser) request.getAttribute(Config.SSO_USER);
        User dbUser = userService.findByUserName(ssoUser.getUserName());
        model.addAttribute("user", dbUser);
        model.addAttribute("currentTab", resourceKey);
        model.addAttribute("ssoSysPath", ssoSysPath);
        model.addAttribute("adminSysPath", adminSysPath);
        model.addAttribute("memberSysPath", memberSysPath);
        model.addAttribute("goodsSysPath", goodsSysPath);
        model.addAttribute("tradeSysPath", tradeSysPath);
        model.addAttribute("operationSysPath", operationSysPath);
        model.addAttribute("fileSysPath", fileSysPath);
        model.addAttribute("settingSysPath", settingSysPath);
        model.addAttribute("statisticsSysPath", statisticsSysPath);
        try {
            model.addAttribute("resources", resourceService.getResourcesByUser(dbUser, resourceKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
