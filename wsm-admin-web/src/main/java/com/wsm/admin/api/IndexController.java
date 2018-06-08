package com.wsm.admin.api;

import com.wsm.admin.model.User;
import com.wsm.admin.service.IResourceService;
import com.wsm.admin.service.IUserService;
import com.wsm.common.api.BaseController;
import com.wsm.sso.config.Config;
import com.wsm.sso.model.SSOUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
	private IUserService userService;
	
	@Autowired
	private IResourceService resourceService;

    @Value("${sso.sys.path}")
    private String ssoSysPath;

	/*@RequestMapping(value = {"/admin", "/admin/login"}, method = RequestMethod.GET)
	public String login(Model model){
		return "login";
	}*/

    /**
     * 控制台首页
     * @return
     */
    @RequestMapping("/home")
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

    /**
     * 会员系统
     * @param model
     * @return
     *//*
    @RequestMapping(value = {"/members/index"}, method = RequestMethod.GET)
    public String members(HttpServletRequest request, Model model){
        getResourcesByUserAndResourceKey(request, model, "members");
        return "index";
    }

    *//**
     * 运营系统
     * @param model
     * @return
     *//*
    @RequestMapping(value = {"/operation/index"}, method = RequestMethod.GET)
    public String operation(HttpServletRequest request, Model model){
        getResourcesByUserAndResourceKey(request, model, "operation");
        return "index";
    }*/

    @RequestMapping(value = {"/admin/login"}, method = RequestMethod.POST)
    public String login(User user, String randomcode, boolean rememberMe, Model model) {
        try {
        	/*String validateCode = (String) request.getSession().getAttribute("validateCode");
			if (randomcode != null && validateCode != null && !randomcode.equals(validateCode)) {
				throw new AuthenticationException("验证码错误");
	        }*/
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword(), rememberMe);
            subject.login(token);
			return redirect("/admin/index");
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "login";
    }
    
    /*@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(User user, String randomcode, Model model){
		try {
			String validateCode = (String) request.getSession().getAttribute("validateCode");
			if (randomcode != null && validateCode != null && !randomcode.equals(validateCode)) {
				throw new Exception("验证码错误");
	        }
			user.setPassword(PasswordUtil.encrypt(user.getPassword(), user.getUserName(), PasswordUtil.getStaticSalt()));
			User dbUser = userService.findByUserNameAndPassword(user.getUserName(), user.getPassword());
			if (dbUser == null){
				throw new Exception("用户名或密码错误！");
			}
			
			model.addAttribute("user", dbUser);
			model.addAttribute("resources", resourceService.getResourcesByUser(dbUser));
			return "index";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "login";
		}
	}*/
	


	@RequestMapping(value = {"/admin/logout"}, method = RequestMethod.POST)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return redirect("admin/login");
    }

    private void getResourcesByUserAndResourceKey(HttpServletRequest request, Model model, String resourceKey) {
        /*Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        User user = (User) principal;
        User dbUser = userService.findByUserName(user.getUserName());
        model.addAttribute("user", dbUser);
        model.addAttribute("currentTab", resourceKey);
        try {
            model.addAttribute("resources", resourceService.getResourcesByUser(dbUser, resourceKey));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        SSOUser ssoUser = (SSOUser) request.getAttribute(Config.SSO_USER);
        User dbUser = userService.findByUserName(ssoUser.getUserName());
        model.addAttribute("user", dbUser);
        model.addAttribute("currentTab", resourceKey);
        model.addAttribute("ssoSysPath", ssoSysPath);
        try {
            model.addAttribute("resources", resourceService.getResourcesByUser(dbUser, resourceKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
