package com.wsm.operation.rpc;

import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
import com.wsm.common.util.ConstantUtils;
import com.wsm.operation.model.Gestbook;
import com.wsm.operation.service.IGestbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operation/gestbook")
public class RpcGestbookController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcGestbookController.class);
    @Autowired
    private IGestbookService gestbookService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxJson save(Gestbook gestbook, String randomcode){
        try{
            AjaxJson ajaxJson = AjaxJson.success(ConstantUtils.SUCCESS_MSG);
            String validateCode = (String) request.getSession().getAttribute("apiValidateCode");
            if (randomcode != null && validateCode != null && !randomcode.equals(validateCode)) {
                ajaxJson = AjaxJson.failure("验证码错误。");
            }else {
                gestbookService.save(gestbook);
            }
            return ajaxJson;
        }catch(Exception e){
            LOGGER.error("系统异常：" + e);
            return AjaxJson.failure("系统异常：" + e);
        }
    }

}
