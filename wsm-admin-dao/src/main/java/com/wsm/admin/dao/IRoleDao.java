package com.wsm.admin.dao;

import com.wsm.admin.model.Role;
import com.wsm.common.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleDao extends IBaseDao<Role, Long>{

	public boolean existsByRoleKey(String roleKey);

}
