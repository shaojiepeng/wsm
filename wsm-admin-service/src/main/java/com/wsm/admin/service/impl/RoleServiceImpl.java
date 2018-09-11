package com.wsm.admin.service.impl;

import com.wsm.admin.dao.IRoleDao;
import com.wsm.admin.model.Role;
import com.wsm.admin.service.IRoleService;
import com.wsm.common.dao.IBaseDao;
import com.wsm.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements IRoleService{

	@Autowired
	private IRoleDao roleDao;
	
//	@Autowired
//	private RoleMapper roleMapper;
	
	@Override
	public IBaseDao<Role, Long> getBaseDao() {
		return this.roleDao;
	}

	@Override
	public List<Role> find(Role role) throws Exception {
//		return roleMapper.select(role);
		return null;
	}

	@Override
	public boolean existsByRoleKey(String roleKey) throws Exception{
		return roleDao.existsByRoleKey(roleKey);
	}
}
