package com.wsm.operation.service;

import com.wsm.common.service.IBaseService;
import com.wsm.operation.model.News;
import com.wsm.operation.vo.NewsVo;

public interface INewsService extends IBaseService<News, Long> {

    public NewsVo encapsulationSimple(News news);

    public NewsVo encapsulationDetail(News news);
}
