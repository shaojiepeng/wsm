package com.wsm.admin.dao;

import com.wsm.admin.model.User;
import com.wsm.common.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends IBaseDao<User, Long>{

	User findByUserNameAndPassword(String userName, String password);

	User findByUserName(String userName);

	boolean existsByUserName(String userName);
	
}
