package com.wsm.file.api;

import com.wsm.admin.feign.consumer.AdminResourceFeignConsumer;
import com.wsm.admin.feign.consumer.AdminUserFeignConsumer;
import com.wsm.admin.vo.UserVo;
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
	private HttpServletRequest request;
    @Autowired
    private AdminUserFeignConsumer userClient;
    @Autowired
    private AdminResourceFeignConsumer resourceClient;

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
     * 运营首页
     * @return
     */
    @RequestMapping("/file/home")
    public String home(){
        return "home";
    }

    /**
     * 运营系统
     * @param model
     * @return
     */
	@RequestMapping(value = {"/file", "/file/index"}, method = RequestMethod.GET)
	public String index(Model model){
        getResourcesByUserAndResourceKey(model, "file");
		return "index";
	}

    private void getResourcesByUserAndResourceKey(Model model, String resourceKey) {
        SSOUser ssoUser = (SSOUser) request.getAttribute(Config.SSO_USER);
        UserVo userVo = userClient.getByUserName(ssoUser.getUserName());

        model.addAttribute("user", userVo);
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
            model.addAttribute("resources", resourceClient.getResourcesByUser(userVo.getUserName(), resourceKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
