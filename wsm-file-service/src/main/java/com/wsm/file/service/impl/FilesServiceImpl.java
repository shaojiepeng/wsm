package com.wsm.file.service.impl;

import com.wsm.common.dao.IBaseDao;
import com.wsm.common.service.impl.BaseServiceImpl;
import com.wsm.file.dao.IFilesDao;
import com.wsm.file.model.Files;
import com.wsm.file.service.IFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("filesService")
@Transactional
public class FilesServiceImpl extends BaseServiceImpl<Files, Long> implements IFilesService {

    @Autowired
    private IFilesDao filesDao;

    @Override
    public IBaseDao<Files, Long> getBaseDao() {
        return this.filesDao;
    }
}
