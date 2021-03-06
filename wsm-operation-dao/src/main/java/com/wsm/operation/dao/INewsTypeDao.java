package com.wsm.operation.dao;

import com.wsm.common.dao.IBaseDao;
import com.wsm.operation.model.NewsType;
import org.springframework.stereotype.Repository;

@Repository
public interface INewsTypeDao extends IBaseDao<NewsType, Long> {
}
