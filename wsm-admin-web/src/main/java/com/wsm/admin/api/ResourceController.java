package com.wsm.admin.api;

import com.wsm.admin.dto.ResourceTree;
import com.wsm.admin.model.Resource;
import com.wsm.admin.model.Role;
import com.wsm.admin.service.IResourceService;
import com.wsm.admin.service.IRoleService;
import com.wsm.admin.util.ResourceTreeUtil;
import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/resource")
public class ResourceController extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("/list")
	public String list(Model model){
		try {
			List<ResourceTree> listTree = resourceService.getTree();
			model.addAttribute("resourceTree", listTree);

			Subject subject = SecurityUtils.getSubject();
			model.addAttribute("editCheck", subject.isPermitted("admin:resource:edit"));
			model.addAttribute("removeCheck", subject.isPermitted("admin:resource:remove"));
		} catch (Exception e) {
			logger.error("系统异常", e);
		}
        return "/admin/resource/list";
    }
	
	@RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
	public String detail(@RequestParam(required = false)String resourceId, Model model){
		Resource resource = new Resource();
		if (resourceId != null){
			resource = resourceService.find(Long.valueOf(resourceId));
		}
		model.addAttribute("parentName", resource.getParentId() != null ? resource.getParentId().getName() : "");
		model.addAttribute("resource", resource);
        return "/admin/resource/form";
    }
	
	/**
     * 新增或修改信息
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson save(Resource resource, String parentResourceId, Model model){
    	try {
	    	if (!StringUtils.isEmpty(resource.getId())){
	    		Resource dbResource = resourceService.find(resource.getId());
	    		if (!dbResource.getResourceKey().equals(resource.getResourceKey())){
					boolean exists = resourceService.existsByResourceKey(resource.getResourceKey());
					if (exists){
						return AjaxJson.failure("该权限标识已存在");
					}
				}
	    		dbResource.setName(resource.getName());
	    		dbResource.setResourceKey(resource.getResourceKey());
	    		dbResource.setResourceType(resource.getResourceType());
	    		dbResource.setUrl(resource.getUrl());
	    		dbResource.setSort(resource.getSort());
				dbResource.setIcon(resource.getIcon());
	    		dbResource.setDescription(resource.getDescription());
	    		resourceService.update(dbResource);
	    	}else{
	    		boolean exists = resourceService.existsByResourceKey(resource.getResourceKey());
				if (exists){
					return AjaxJson.failure("该权限标识已存在");
				}

				if (!StringUtils.isEmpty(parentResourceId)){
					Resource parentResource = resourceService.find(Long.valueOf(parentResourceId));
					resource.setParentId(parentResource);
				}
				resourceService.save(resource);
	    	}
	    	return AjaxJson.success("操作成功");
    	}
    	catch (Exception e) {
			logger.error("系统异常", e);
			return AjaxJson.failure("系统异常：" + e);
		}
    }
    
    /**
     * 删除角色
     */
    @RequestMapping(value = {"/remove"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson remove(String resourceId, Model model){
    	try {
    		if (!StringUtils.isEmpty(resourceId)){
    			Resource resource = resourceService.find(Long.valueOf(resourceId));
    			Sort sort = new Sort(Sort.Direction.DESC, "createTime");
    			List<Resource> childResource = resourceService.findList(new Specification<Resource>(){
					@Override
					public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						List<Predicate> predicates = new ArrayList<Predicate>();
						predicates.add(cb.equal(root.get("parentId").as(Resource.class), resource));
						return cb.and(predicates.toArray(new Predicate[predicates.size()]));
					}
    			}, sort);
    			
    			if (childResource != null && childResource.size() > 0){
    				return AjaxJson.failure("该资源拥有子资源，不能删除");
    			}
    			resource.setParentId(null);
    			resource.setRecStatus("I");
    			resourceService.update(resource);
    			return AjaxJson.success("操作成功");
    		}
    		return AjaxJson.failure("资源id不能为空");
    	} catch (Exception e) {
    		logger.error("系统异常", e);
    		return AjaxJson.failure("系统异常：" + e);
    	}
    }
	
	@RequestMapping(value = {"/getByRoleId"}, method = RequestMethod.GET)
	@ResponseBody
	public List<ResourceTree> getResourcesByRoleId(String roleId){
		Role role = new Role();
		List<Resource> all = resourceService.findAll();
		List<Resource> in = new ArrayList<>();
		if (roleId != null){
			role = roleService.find(Long.valueOf(roleId));
			role.getResources().forEach(resource -> in.add(resource));
		}
		ResourceTreeUtil resourceTreeUtil = new ResourceTreeUtil();
		return resourceTreeUtil.buildTree(all, in);
	}

}
