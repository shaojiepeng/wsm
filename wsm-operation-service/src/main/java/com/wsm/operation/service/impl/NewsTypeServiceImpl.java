package com.wsm.operation.service.impl;

import com.wsm.common.dao.IBaseDao;
import com.wsm.common.service.impl.BaseServiceImpl;
import com.wsm.operation.dao.INewsTypeDao;
import com.wsm.operation.dto.NewsTypeTree;
import com.wsm.operation.model.NewsType;
import com.wsm.operation.service.INewsTypeService;
import com.wsm.operation.util.NewsTypeTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("newsTypeService")
@Transactional
public class NewsTypeServiceImpl extends BaseServiceImpl<NewsType, Long> implements INewsTypeService {

    @Autowired
    private INewsTypeDao newsTypeDao;

    @Override
    public IBaseDao<NewsType, Long> getBaseDao() {
        return this.newsTypeDao;
    }


    @Override
    public List<NewsTypeTree> getTree() throws Exception{
        List<NewsType> newsTypes = newsTypeDao.findAll();
        NewsTypeTreeUtil tree = new NewsTypeTreeUtil(newsTypes);
        return tree.buildTree();
    }
}
