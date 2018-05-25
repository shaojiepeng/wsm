package com.wsm.admin.service;

import com.wsm.admin.dto.ResourceTree;
import com.wsm.admin.model.Resource;
import com.wsm.admin.model.User;
import com.wsm.common.service.IBaseService;

import java.util.List;

public interface IResourceService extends IBaseService<Resource, Long>{
	
	public List<ResourceTree> getResourcesByUser(User user) throws Exception;

	public List<Resource> getAllResources() throws Exception;

	public List<ResourceTree> getTree() throws Exception;

	public boolean existsByResourceKey(String resourceKey) throws Exception;

}
