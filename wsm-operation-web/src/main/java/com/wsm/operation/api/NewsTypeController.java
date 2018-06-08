package com.wsm.operation.api;

import com.wsm.common.api.BaseController;
import com.wsm.operation.dto.NewsTypeTree;
import com.wsm.operation.service.INewsTypeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/operation/newsType")
public class NewsTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private INewsTypeService newsTypeService;

    @RequestMapping("/list")
    public String list(Model model){
        try {
            List<NewsTypeTree> listTree = newsTypeService.getTree();
            model.addAttribute("newsTypeTree", listTree);

            Subject subject = SecurityUtils.getSubject();
            model.addAttribute("editCheck", subject.isPermitted("operation:newsType:edit"));
            model.addAttribute("removeCheck", subject.isPermitted("operation:newsType:remove"));
        } catch (Exception e) {
            logger.error("系统异常", e);
        }
        return "/operation/newsType/list";
    }
}
