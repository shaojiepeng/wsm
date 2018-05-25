package com.wsm.admin.service;

import com.wsm.admin.model.Role;
import com.wsm.common.service.IBaseService;

import java.util.List;

public interface IRoleService extends IBaseService<Role, Long>{
	
	public List<Role> find(Role role) throws Exception;

	public boolean existsByRoleKey(String roleKey) throws Exception;

}
