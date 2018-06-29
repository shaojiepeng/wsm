package com.wsm.operation.service.impl;

import com.wsm.common.dao.IBaseDao;
import com.wsm.common.service.impl.BaseServiceImpl;
import com.wsm.operation.dao.INewsDao;
import com.wsm.operation.model.News;
import com.wsm.operation.service.INewsService;
import com.wsm.operation.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("newsService")
@Transactional
public class NewsServiceImpl extends BaseServiceImpl<News, Long> implements INewsService {

    @Autowired
    private INewsDao newsDao;

    @Override
    public IBaseDao<News, Long> getBaseDao() {
        return this.newsDao;
    }

    @Override
    public NewsVo encapsulationSimple(News news) {
        NewsVo vo = new NewsVo();
        vo.setId(news.getId());
        vo.setTitle(news.getTitle());
        vo.setReleaseDate(news.getReleaseDate());
        vo.setIntro(news.getIntro());
        vo.setImageUrl(news.getImageUrl());
        vo.setUpdateTime(news.getUpdateTime());
        vo.setCreateTime(news.getCreateTime());
        if (news.getNewsType() != null) {
            vo.setNewsTypeName(news.getNewsType().getName());
        }
        return vo;
    }

    @Override
    public NewsVo encapsulationDetail(News news) {
        NewsVo vo = encapsulationSimple(news);
        vo.setContent(news.getContent());
        vo.setRecStatus(news.getRecStatus());
        return vo;
    }
}
