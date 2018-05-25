package com.wsm.admin.api;

import com.wsm.admin.model.Role;
import com.wsm.admin.model.User;
import com.wsm.admin.service.IRoleService;
import com.wsm.admin.service.IUserService;
import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
import com.wsm.common.util.PasswordUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("/list")
	public String list(Model model){
		Page<User> userList = userService.findAll(getPageRequest(new Sort(Sort.Direction.DESC, "createTime")));
		model.addAttribute("users", userList);
		return "/admin/user/list";
    }
	
	/**
	 * 修改管理员信息
	 */
	@RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
	public String detail(@RequestParam(required = false)String userId, Model model) throws Exception {
		User dbUser = new User();
		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
        User user = (User) principal;
        
        List<String> checkRoleIds = new ArrayList<>();
		if (userId != null){
			dbUser = userService.find(Long.valueOf(userId));
			
			for (Role role : dbUser.getRoles()) {
				checkRoleIds.add(role.getId().toString());
			}
		}
		
		Role role = new Role();
		role.setRecStatus("A");
		
        model.addAttribute("currentId", user.getId());
        model.addAttribute("user", dbUser);
        //待选角色列表
        model.addAttribute("roles", roleService.find(role));
        //已勾选角色ID
        model.addAttribute("checkRoleId",String.join(",",checkRoleIds));
		return "/admin/user/form";
	}
	
    /**
     * 新增或修改管理员信息
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
	public AjaxJson save(User user, int[] roleIds, Model model) {
		try {
			if (!StringUtils.isEmpty(user.getId())){
				User dbUser = userService.find(user.getId());
				dbUser.setUserName(user.getUserName());
				dbUser.setRealName(user.getRealName());
				dbUser.setStatus(user.getStatus());
				dbUser.setAvatar(user.getAvatar());
				
				Set<Role> roles = null;
				if (roleIds != null && roleIds.length > 0){
					roles = new HashSet<>();
					for (int i : roleIds){
						roles.add(roleService.find(Long.valueOf(i)));
					}
				}
				dbUser.setRoles(roles);
				userService.update(dbUser);
			}else{
				boolean exists = userService.existsByUserName(user.getUserName());
				if (exists){
					return AjaxJson.failure("该用户名已存在");
				}
				user.setPassword(PasswordUtil.encrypt("123456", user.getUserName(), PasswordUtil.getStaticSalt()));
				
				Set<Role> roles = null;
				if (roleIds != null && roleIds.length > 0){
					roles = new HashSet<>();
					for (int i : roleIds){
						roles.add(roleService.find(Long.valueOf(i)));
					}
				}
				user.setRoles(roles);
				userService.save(user);
			}
			return AjaxJson.success("操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AjaxJson.failure(e.getMessage());
		}
	}
    
    /**
     * 重置密码
     */
    @RequestMapping(value = {"/resetPass"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson resetPass(String userId, Model model) {
    	try {
    		if (!StringUtils.isEmpty(userId)){
    			User dbUser = userService.find(Long.valueOf(userId));
    			dbUser.setPassword(PasswordUtil.encrypt("123456", dbUser.getUserName(), PasswordUtil.getStaticSalt()));
    			userService.update(dbUser);
    			return AjaxJson.success("操作成功");
    		}
    		return AjaxJson.failure("用户id不能为空");
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		return AjaxJson.failure(e.getMessage());
    	}
    }
    
    /**
     * 删除用户
     */
    @RequestMapping(value = {"/remove"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson remove(String userId, Model model) {
    	try {
    		if (!StringUtils.isEmpty(userId)){
    			User dbUser = userService.find(Long.valueOf(userId));
    			dbUser.setRoles(null);
    			dbUser.setRecStatus("I");
    			userService.update(dbUser);
    			return AjaxJson.success("操作成功");
    		}
    		return AjaxJson.failure("用户id不能为空");
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		return AjaxJson.failure(e.getMessage());
    	}
    }

	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping("/altPwd")
	public String altPwd(){
		return "/admin/user/updatePwd";
	}

	/**
	 * 修改密码
	 */
	@RequestMapping("/updatePwd")
	@ResponseBody
	public AjaxJson updatePwd(@RequestParam String oldPassword, @RequestParam String password){
		try {
			Subject subject = SecurityUtils.getSubject();
			Object principal = subject.getPrincipal();
			User user = (User) principal;

			if(!PasswordUtil.encrypt(oldPassword, user.getUserName(), PasswordUtil.getStaticSalt()).equals(user.getPassword())){
				return AjaxJson.failure("原密码错误");
			}

			user.setPassword(PasswordUtil.encrypt(password, user.getUserName(), PasswordUtil.getStaticSalt()));
			userService.update(user);
			return AjaxJson.success("操作成功");
		}catch (Exception e){
			e.printStackTrace();
			return AjaxJson.failure("操作异常");
		}
	}
	
	
}
