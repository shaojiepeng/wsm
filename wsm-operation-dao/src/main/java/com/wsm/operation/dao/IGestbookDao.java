package com.wsm.operation.dao;

import com.wsm.common.dao.IBaseDao;
import com.wsm.operation.model.Gestbook;
import org.springframework.stereotype.Repository;

@Repository
public interface IGestbookDao extends IBaseDao<Gestbook, Long> {
}
