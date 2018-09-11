package com.wsm.operation.api;

import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
import com.wsm.common.util.ConstantUtils;
import com.wsm.operation.dto.NewsTypeTree;
import com.wsm.operation.model.News;
import com.wsm.operation.model.NewsType;
import com.wsm.operation.service.INewsService;
import com.wsm.operation.service.INewsTypeService;
import com.wsm.operation.util.NewsTypeTreeUtil;
import com.wsm.sso.config.Config;
import com.wsm.sso.model.SSOUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/operation/newsType")
public class NewsTypeController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsTypeController.class);

    @Autowired
    private INewsService newsService;
    @Autowired
    private INewsTypeService newsTypeService;

    @RequestMapping("/list")
    public String list(Model model) {
        try {
            List<NewsTypeTree> listTree = newsTypeService.getTree();
            model.addAttribute("newsTypeTree", listTree);

            boolean hasEditCheck = false;
            boolean hasRemoveCheck = false;
            SSOUser ssoUser = (SSOUser) request.getAttribute(Config.SSO_USER);
            if (ssoUser != null && ssoUser.getPermissionSet() != null){
                Set<String> permissionSet = ssoUser.getPermissionSet();
                if (permissionSet.contains("operation:newsType:edit")){
                    hasEditCheck = true;
                }
                if (permissionSet.contains("operation:newsType:remove")){
                    hasRemoveCheck = true;
                }
            }

            model.addAttribute("editCheck", hasEditCheck);
            model.addAttribute("removeCheck", hasRemoveCheck);
        } catch (Exception e) {
            LOGGER.error("系统异常", e);
        }
        return "/operation/newsType/list";
    }

    @RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
    public String detail(@RequestParam(required = false) String newsTypeId, Model model) {
        NewsType newsType = new NewsType();
        if (newsTypeId != null) {
            newsType = newsTypeService.find(Long.valueOf(newsTypeId));
        }
        model.addAttribute("parentName", newsType.getParentId() != null ? newsType.getParentId().getName() : "");
        model.addAttribute("newsType", newsType);
        return "/operation/newsType/form";
    }

    /**
     * 新增或修改新闻分类
     */
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson save(NewsType newsType, String parentNewsTypeId, Model model) {
        try {
            AjaxJson ajaxJson = AjaxJson.success(ConstantUtils.SUCCESS_MSG);
            if (!StringUtils.isEmpty(newsType.getId())) {
                NewsType dbNewsType = newsTypeService.find(newsType.getId());
                dbNewsType.setName(newsType.getName());
                dbNewsType.setDescription(newsType.getDescription());
                if (!StringUtils.isEmpty(parentNewsTypeId)) {
                    NewsType parentNewsType = newsTypeService.find(Long.valueOf(parentNewsTypeId));
                    dbNewsType.setParentId(parentNewsType);
                } else {
                    dbNewsType.setParentId(null);
                }
                newsTypeService.update(dbNewsType);
            } else {
                if (!StringUtils.isEmpty(parentNewsTypeId)) {
                    NewsType parentNewsType = newsTypeService.find(Long.valueOf(parentNewsTypeId));
                    newsType.setParentId(parentNewsType);
                }
                newsTypeService.save(newsType);
            }
            return ajaxJson;
        } catch (Exception e) {
            LOGGER.error("系统异常：", e);
            return AjaxJson.failure("系统异常：" + e);
        }
    }


    @RequestMapping(value = {"/remove"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson remove(String newsTypeId, Model model) {
        try {
            AjaxJson ajaxJson = AjaxJson.success(ConstantUtils.SUCCESS_MSG);
            if (!StringUtils.isEmpty(newsTypeId)) {
                NewsType newsType = newsTypeService.find(Long.valueOf(newsTypeId));
                Sort sort = new Sort(Sort.Direction.DESC, "createTime");
                List<NewsType> childNewsType = newsTypeService.findList(new Specification<NewsType>() {
                    @Override
                    public Predicate toPredicate(Root<NewsType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                        List<Predicate> predicates = new ArrayList<Predicate>();
                        predicates.add(cb.equal(root.get("parentId").as(NewsType.class), newsType));
                        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }, sort);

                if (childNewsType != null && childNewsType.size() > 0) {
                    ajaxJson = AjaxJson.failure("该分类拥有子分类，不能删除");
                }else {
                    newsType.setParentId(null);
                    newsType.setRecStatus("I");
                    newsTypeService.update(newsType);
                }
            }else{
                ajaxJson = AjaxJson.failure("分类id不能为空");
            }
            return ajaxJson;
        } catch (Exception e) {
            LOGGER.error("系统异常：", e);
            return AjaxJson.failure("系统异常：" + e);
        }
    }

    @RequestMapping(value = {"/getTreeByNews"}, method = RequestMethod.GET)
    @ResponseBody
    public List<NewsTypeTree> getTreeByNews(String newsId) {
        News news = new News();
        List<NewsType> all = newsTypeService.findAll();
        List<NewsType> in = new ArrayList<>();
        if (newsId != null) {
            news = newsService.find(Long.valueOf(newsId));
            in.add(news.getNewsType());
        }
        NewsTypeTreeUtil util = new NewsTypeTreeUtil();
        return util.buildTree(all, in);
    }

    @RequestMapping(value = {"/getTreeByNewsType"}, method = RequestMethod.GET)
    @ResponseBody
    public List<NewsTypeTree> getTreeByNewsType(String newsTypeId) {
        NewsType newsType = new NewsType();
        List<NewsType> all = newsTypeService.findAll();
        List<NewsType> in = new ArrayList<>();
        if (newsTypeId != null) {
            newsType = newsTypeService.find(Long.valueOf(newsTypeId));
            if (newsType.getParentId() != null) {
                in.add(newsType.getParentId());
            }
        }
        NewsTypeTreeUtil util = new NewsTypeTreeUtil();
        return util.buildTree(all, in);
    }
}
