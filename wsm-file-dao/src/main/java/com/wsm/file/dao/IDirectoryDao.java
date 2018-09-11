package com.wsm.file.dao;

import com.wsm.common.dao.IBaseDao;
import com.wsm.file.model.Directory;
import org.springframework.stereotype.Repository;

@Repository
public interface IDirectoryDao extends IBaseDao<Directory, Long> {
}
