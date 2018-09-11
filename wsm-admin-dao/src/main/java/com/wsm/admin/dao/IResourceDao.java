package com.wsm.admin.dao;

import com.wsm.admin.model.Resource;
import com.wsm.common.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface IResourceDao extends IBaseDao<Resource, Long>{

	boolean existsByResourceKey(String resourceKey);

}
