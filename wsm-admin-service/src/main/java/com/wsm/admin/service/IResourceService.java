package com.wsm.admin.service;

import com.wsm.admin.dto.ResourceTree;
import com.wsm.admin.model.Resource;
import com.wsm.admin.model.User;
import com.wsm.common.service.IBaseService;

import java.util.List;

public interface IResourceService extends IBaseService<Resource, Long>{
	
	List<ResourceTree> getResourcesByUser(User user, String resourceKey) throws Exception;

	List<Resource> getAllResources() throws Exception;

	List<ResourceTree> getTree() throws Exception;

	boolean existsByResourceKey(String resourceKey) throws Exception;

}
