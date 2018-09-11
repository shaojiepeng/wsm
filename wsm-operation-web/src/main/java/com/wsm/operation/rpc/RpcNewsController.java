package com.wsm.operation.rpc;

import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
import com.wsm.operation.model.News;
import com.wsm.operation.model.NewsType;
import com.wsm.operation.service.INewsService;
import com.wsm.operation.service.INewsTypeService;
import com.wsm.operation.vo.NewsVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/operation/news")
public class RpcNewsController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcNewsController.class);
    @Autowired
    private INewsService newsService;
    @Autowired
    private INewsTypeService newsTypeService;

    @RequestMapping(value = "/getByType", method = RequestMethod.GET)
    public AjaxJson getNewsByType(String newsTypeId, @RequestParam(value="page", defaultValue="1")int page){
        try {
            AjaxJson ajaxJson = null;
            if (StringUtils.isNotEmpty(newsTypeId)){
                NewsType newsType = newsTypeService.find(Long.valueOf(newsTypeId));

                Pageable pageable = new PageRequest(--page, 10, new Sort(Sort.Direction.DESC, "releaseDate"));
                Page<News> newsList = newsService.findAll(new Specification<News>(){
                    @Override
                    public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                        List<Predicate> predicates = new ArrayList<Predicate>();
                        predicates.add(cb.equal(root.get("newsType").as(NewsType.class), newsType));
                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }, pageable);

                List<NewsVo> newsVos = new ArrayList<>();
                if (newsList != null && newsList.getContent().size() > 0) {
                    for (News news : newsList.getContent()) {
                        newsVos.add(newsService.encapsulationSimple(news));
                    }
                }
                Page<NewsVo> exPage = new PageImpl<NewsVo>(newsVos, pageable, newsList.getTotalElements());
                ajaxJson = AjaxJson.success(exPage);
            }else{
                ajaxJson = AjaxJson.failure("新闻类型ID不能为空。");
            }
            return ajaxJson;
        }catch(Exception e){
            LOGGER.error("系统异常：" + e);
            return AjaxJson.failure("系统异常：" + e);
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public AjaxJson find(String newsId){
        try{
            AjaxJson ajaxJson = null;
            if (StringUtils.isNotEmpty(newsId)){
                News news = newsService.find(Long.valueOf(newsId));
                ajaxJson = AjaxJson.success(newsService.encapsulationDetail(news));
            }else {
                ajaxJson = AjaxJson.failure("新闻ID不能为空。");
            }
            return ajaxJson;
        }catch(Exception e){
            LOGGER.error("系统异常：" + e);
            return AjaxJson.failure("系统异常：" + e);
        }
    }



}
