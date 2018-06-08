package com.wsm.operation.service;

import com.wsm.common.service.IBaseService;
import com.wsm.operation.dto.NewsTypeTree;
import com.wsm.operation.model.NewsType;

import java.util.List;

public interface INewsTypeService extends IBaseService<NewsType, Long> {

    public List<NewsTypeTree> getTree() throws Exception;
}
