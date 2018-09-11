package com.wsm.file.dao;

import com.wsm.common.dao.IBaseDao;
import com.wsm.file.model.Files;
import org.springframework.stereotype.Repository;

@Repository
public interface IFilesDao extends IBaseDao<Files, Long> {
}
