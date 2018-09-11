package com.wsm.admin.service;

import com.wsm.admin.model.User;
import com.wsm.common.service.IBaseService;

public interface IUserService extends IBaseService<User, Long>{

	User findByUserNameAndPassword(String userName, String password) throws Exception;

	User findByUserName(String userName);

	boolean existsByUserName(String userName) throws Exception;

	void clearCache(User user);
}
