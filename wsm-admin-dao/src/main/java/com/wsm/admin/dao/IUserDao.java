package com.wsm.admin.dao;

import com.wsm.admin.model.User;
import com.wsm.common.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends IBaseDao<User, Long>{

	public User findByUserNameAndPassword(String userName, String password);

	public User findByUserName(String userName);

	public boolean existsByUserName(String userName);
	
}
