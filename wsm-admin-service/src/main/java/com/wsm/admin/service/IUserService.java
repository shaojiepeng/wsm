package com.wsm.admin.service;

import com.wsm.admin.model.User;
import com.wsm.common.service.IBaseService;

import java.util.List;

public interface IUserService extends IBaseService<User, Long>{

	public List<User> find(User user) throws Exception;

	public User findByUserNameAndPassword(String userName, String password) throws Exception;

	public User findByUserName(String userName);

	public boolean existsByUserName(String userName) throws Exception;

	public void clearCache(User user);
}
