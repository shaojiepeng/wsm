package com.wsm.admin.api;

import com.wsm.admin.model.User;
import com.wsm.admin.service.IResourceService;
import com.wsm.admin.service.IUserService;
import com.wsm.common.api.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value = {"/admin", "/admin/login"}, method = RequestMethod.GET)
	public String login(Model model){
		return "login";
	}
	
	@RequestMapping(value = {"/admin/index"}, method = RequestMethod.GET)
	public String index(Model model){
		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
        User user = (User) principal;
        User dbUser = userService.findByUserName(user.getUserName());
        model.addAttribute("user", dbUser);
		try {
			model.addAttribute("resources", resourceService.getResourcesByUser(dbUser));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
	
    @RequestMapping("/home")
    public String home(){
        return "home";
    }
    
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
}
