package com.wsm.admin.service;

import com.wsm.admin.model.Role;
import com.wsm.common.service.IBaseService;

import java.util.List;

public interface IRoleService extends IBaseService<Role, Long>{
	
	List<Role> find(Role role) throws Exception;

	boolean existsByRoleKey(String roleKey) throws Exception;

}
