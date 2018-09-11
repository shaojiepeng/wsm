package com.wsm.operation.api;

import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
import com.wsm.common.util.ConstantUtils;
import com.wsm.operation.model.Gestbook;
import com.wsm.operation.service.IGestbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/operation/gestbook")
public class GestbookController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestbookController.class);
    @Autowired
    private IGestbookService gestbookService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        Pageable pageable = getPageRequest(new Sort(Sort.Direction.DESC, "createTime"));
        Page<Gestbook> list = gestbookService.findAll(pageable);
        model.addAttribute("gestbookList", list);
        return "/operation/gestbook/list";
    }

    @RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
    public String detail(@RequestParam(required = false) String gestbookId, Model model) {
        Gestbook gestbook = new Gestbook();
        if (gestbookId != null) {
            gestbook = gestbookService.find(Long.valueOf(gestbookId));
        }
        model.addAttribute("gestbook", gestbook);
        return "/operation/gestbook/form";
    }


    @RequestMapping(value = {"/remove"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson remove(String gestbookId, Model model) {
        try {
            AjaxJson ajaxJson = AjaxJson.success(ConstantUtils.SUCCESS_MSG);
            if (!StringUtils.isEmpty(gestbookId)) {
                Gestbook gestbook = gestbookService.find(Long.valueOf(gestbookId));
                gestbook.setRecStatus("I");
                gestbookService.update(gestbook);
            }else {
                ajaxJson = AjaxJson.failure("留言id不能为空");
            }
            return ajaxJson;
        } catch (Exception e) {
            LOGGER.error("系统异常：", e);
            return AjaxJson.failure("系统异常：" + e);
        }
    }

}
