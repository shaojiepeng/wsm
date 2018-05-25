package com.wsm.admin.api;

import com.wsm.admin.model.Resource;
import com.wsm.admin.model.Role;
import com.wsm.admin.service.IRoleService;
import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("/list")
	public String list(Model model){
		Page<Role> roleList = roleService.findAll(getPageRequest(new Sort(Sort.Direction.DESC, "createTime")));
		model.addAttribute("roles", roleList);
        return "/admin/role/list";
    }
	
	@RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
	public String detail(@RequestParam(required = false)String roleId, Model model) throws Exception {
		Role role = new Role();
		if (roleId != null){
			role = roleService.find(Long.valueOf(roleId));
			Set<Resource> resources = role.getResources();
			StringBuffer sb = new StringBuffer();
			int i = 1;
			for(Iterator<Resource> it = resources.iterator();it.hasNext(); ++i){
				sb.append(it.next().getName()).append(i == resources.size() ? "" : "，");
			}
			model.addAttribute("checkNodesName", sb.toString());
		}
        model.addAttribute("role", role);
		return "/admin/role/form";
	}
	
	/**
     * 新增或修改角色信息
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
	public AjaxJson save(Role role, String resourceIds, Model model) {
		try {
			Set<Resource> resources = new HashSet<>();
			if (resourceIds != null){
				String[] resourceIdArr = resourceIds.split(",");
				for (String id : resourceIdArr){
					Resource resource = new Resource();
					resource.setId(Long.valueOf(id));
					resources.add(resource);
				}
			}
			
			if (!StringUtils.isEmpty(role.getId())){
				Role dbRole = roleService.find(role.getId());
				if (!dbRole.getRoleKey().equals(role.getRoleKey())){
					boolean exists = roleService.existsByRoleKey(role.getRoleKey());
					if (exists){
						return AjaxJson.failure("该标识符已存在");
					}
				}
				dbRole.setRoleName(role.getRoleName());
				dbRole.setRoleKey(role.getRoleKey());
				dbRole.setRoleDesc(role.getRoleDesc());
				
				dbRole.setResources(resources);
				roleService.update(dbRole);
			}else{
				boolean exists = roleService.existsByRoleKey(role.getRoleKey());
				if (exists){
					return AjaxJson.failure("该标识符已存在");
				}
				role.setResources(resources);
				roleService.save(role);
			}
			return AjaxJson.success("操作成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return AjaxJson.failure(e.getMessage());
		}
	}
    
    /**
     * 删除角色
     */
    @RequestMapping(value = {"/remove"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson remove(String roleId, Model model) {
    	try {
    		if (!StringUtils.isEmpty(roleId)){
    			Role dbRole = roleService.find(Long.valueOf(roleId));
    			dbRole.setResources(null);
    			dbRole.setRecStatus("I");
    			roleService.update(dbRole);
    			return AjaxJson.success("操作成功");
    		}
    		return AjaxJson.failure("角色id不能为空");
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    		return AjaxJson.failure(e.getMessage());
    	}
    }

}