package com.wsm.operation.api;

import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
import com.wsm.common.util.ConstantUtils;
import com.wsm.operation.model.News;
import com.wsm.operation.model.NewsType;
import com.wsm.operation.service.INewsService;
import com.wsm.operation.service.INewsTypeService;
import com.wsm.operation.vo.NewsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/operation/news")
public class NewsController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private INewsService newsService;
    @Autowired
    private INewsTypeService newsTypeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        Pageable pageable = getPageRequest(new Sort(Sort.Direction.DESC, "releaseDate"));
        Page<News> newsList = newsService.findAll(pageable);
        List<NewsVo> newsVos = new ArrayList<>();
        if (newsList != null && newsList.getContent().size() > 0) {
            for (News news : newsList.getContent()) {
                newsVos.add(newsService.encapsulationSimple(news));
            }
        }
        Page<NewsVo> exPage = new PageImpl<NewsVo>(newsVos, pageable, newsList.getTotalElements());
        model.addAttribute("newsList", exPage);
        return "/operation/news/list";
    }

    @RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
    public String detail(@RequestParam(required = false) String newsId, Model model) {
        News news = new News();
        if (newsId != null) {
            news = newsService.find(Long.valueOf(newsId));
            if (news != null && news.getNewsType() != null) {
                model.addAttribute("newsTypeName", news.getNewsType().getName());
            }
        }else {
            news.setNewsType(new NewsType());
        }

        model.addAttribute("news", news);
        return "/operation/news/form";
    }

    /**
     * 新增或修改新闻分类
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson save(News news, String newsTypeId, Model model) {
        try {
            if (!StringUtils.isEmpty(news.getId())) {
                News dbNews = newsService.find(news.getId());
                dbNews.setTitle(news.getTitle());
                dbNews.setContent(news.getContent());
                dbNews.setIntro(news.getIntro());
                dbNews.setReleaseDate(news.getReleaseDate());
                dbNews.setImageUrl(news.getImageUrl());
                if (newsTypeId != null) {
                    NewsType newsType = newsTypeService.find(Long.valueOf(newsTypeId));
                    dbNews.setNewsType(newsType);
                }
                newsService.update(dbNews);
            } else {
                if (newsTypeId != null) {
                    NewsType newsType = newsTypeService.find(Long.valueOf(newsTypeId));
                    news.setNewsType(newsType);
                }
                newsService.save(news);
            }
            return AjaxJson.success(ConstantUtils.SUCCESS_MSG);
        } catch (Exception e) {
            LOGGER.error("系统异常：", e);
            return AjaxJson.failure("系统异常：" + e);
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = {"/remove"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson remove(String newsId, Model model) {
        try {
            AjaxJson ajaxJson = AjaxJson.success(ConstantUtils.SUCCESS_MSG);
            if (!StringUtils.isEmpty(newsId)) {
                News dbnews = newsService.find(Long.valueOf(newsId));
                dbnews.setRecStatus("I");
                newsService.update(dbnews);
            }else {
                ajaxJson = AjaxJson.failure("新闻id不能为空");
            }
            return ajaxJson;
        } catch (Exception e) {
            LOGGER.error("系统异常：", e);
            return AjaxJson.failure("系统异常：" + e);
        }
    }


}
