package com.wsm.admin.service.impl;

import com.wsm.admin.dao.IUserDao;
import com.wsm.admin.model.User;
import com.wsm.admin.service.IUserService;
import com.wsm.common.dao.IBaseDao;
import com.wsm.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService{
	
	@Autowired
    private IUserDao userDao;
	
	@Override
	public IBaseDao<User, Long> getBaseDao() {
		return this.userDao;
	}

	@Override
	public User findByUserName(String userName){
		return userDao.findByUserName(userName);
	}

	@Override
	public User findByUserNameAndPassword(String userName, String password) throws Exception{
		return userDao.findByUserNameAndPassword(userName, password);
	}

	@Override
	public boolean existsByUserName(String userName) throws Exception{
		return userDao.existsByUserName(userName);
	}

	@Override
	@CacheEvict(value="resourcesUserCache", allEntries=true)
	public void clearCache(User user) {}


}
