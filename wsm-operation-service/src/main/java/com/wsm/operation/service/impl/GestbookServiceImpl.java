package com.wsm.operation.service.impl;

import com.wsm.common.dao.IBaseDao;
import com.wsm.common.service.impl.BaseServiceImpl;
import com.wsm.operation.dao.IGestbookDao;
import com.wsm.operation.model.Gestbook;
import com.wsm.operation.service.IGestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gestbookService")
@Transactional
public class GestbookServiceImpl extends BaseServiceImpl<Gestbook, Long> implements IGestbookService {

    @Autowired
    private IGestbookDao gestbookDao;

    @Override
    public IBaseDao<Gestbook, Long> getBaseDao() {
        return this.gestbookDao;
    }
}
