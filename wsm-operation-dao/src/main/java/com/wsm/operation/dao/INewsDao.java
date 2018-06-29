package com.wsm.operation.dao;

import com.wsm.common.dao.IBaseDao;
import com.wsm.operation.model.News;
import org.springframework.stereotype.Repository;

@Repository
public interface INewsDao extends IBaseDao<News, Long> {
}
