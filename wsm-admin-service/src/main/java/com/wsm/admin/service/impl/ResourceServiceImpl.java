package com.wsm.admin.service.impl;

import com.wsm.admin.dao.IResourceDao;
import com.wsm.admin.dto.ResourceTree;
import com.wsm.admin.mapper.ResourceMapper;
import com.wsm.admin.model.Resource;
import com.wsm.admin.model.Role;
import com.wsm.admin.model.User;
import com.wsm.admin.service.IResourceService;
import com.wsm.admin.util.ResourceTreeUtil;
import com.wsm.common.dao.IBaseDao;
import com.wsm.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("resourceService")
@Transactional
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Long> implements IResourceService{
	
	@Autowired
	private IResourceDao resourceDao;
	
	@Autowired
	private ResourceMapper resourceMapper;
	
	@Override
	public IBaseDao<Resource, Long> getBaseDao() {
		return this.resourceDao;
	}
	
	@Override
	public List<Resource> getAllResources() throws Exception{
		return resourceMapper.selectAll();
	}

	@Override
	@Cacheable(value = "resourcesUserCache", key = "#root.caches[0].name + ':' + #user.id")
	public List<ResourceTree> getResourcesByUser(User user) throws Exception {
		//Get user menu
		Set<Role> roles = user.getRoles();
		List<Resource> resources = new ArrayList<>();
		roles.forEach(role -> {
			for (Resource resource : role.getResources()){
				if ("menu".equals(resource.getResourceType())){
					resources.add(resource);
				}
			}
		});
//		List<Resource> resources = getAllResources();
		ResourceTreeUtil tree = new ResourceTreeUtil(resources);
        List<ResourceTree> listTree = tree.buildTree();
		return listTree;
	}

	@Override
	@Cacheable(value = "resourcesCache")
	public List<ResourceTree> getTree() throws Exception {
		List<Resource> resources = resourceDao.findAll();
		ResourceTreeUtil tree = new ResourceTreeUtil(resources);
		return tree.buildTree();
	}

	@Override
	public boolean existsByResourceKey(String resourceKey) throws Exception {
		return resourceDao.existsByResourceKey(resourceKey);
	}

}
