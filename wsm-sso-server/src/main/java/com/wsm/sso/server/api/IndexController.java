package com.wsm.sso.server.api;

import com.wsm.admin.feign.consumer.service.AdminUserFeignConsumer;
import com.wsm.admin.feign.consumer.vo.UserVo;
import com.wsm.common.util.PasswordUtil;
import com.wsm.sso.config.Config;
import com.wsm.sso.exception.SSOException;
import com.wsm.sso.model.SSOUser;
import com.wsm.sso.util.SSOLoginHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * sso server (for web)
 *
 */
@Controller
public class IndexController {

    @Autowired
    private AdminUserFeignConsumer client;

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {

        // login check
        SSOUser ssoUser = SSOLoginHelper.loginCheck(request);

        if (ssoUser == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("ssoUser", ssoUser);
            return "index";
        }
    }

    /**
     * Login page
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(Config.SSO_LOGIN)
    public String login(Model model, HttpServletRequest request) {

        // login check


        SSOUser ssoUser = SSOLoginHelper.loginCheck(request);

        if (ssoUser != null) {

            // success redirect
            String redirectUrl = request.getParameter(Config.REDIRECT_URL);
            if (StringUtils.isNotBlank(redirectUrl)) {

                String sessionId = SSOLoginHelper.getSessionIdByCookie(request);
                String redirectUrlFinal = redirectUrl + "?" + Config.SSO_SESSIONID + "=" + sessionId;;

                return "redirect:" + redirectUrlFinal;
            } else {
                return "redirect:/";
            }
        }

        model.addAttribute("errorMsg", request.getParameter("errorMsg"));
        model.addAttribute(Config.REDIRECT_URL, request.getParameter(Config.REDIRECT_URL));
        return "login";
    }

    /**
     * Login
     *
     * @param request
     * @param redirectAttributes
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes,
                        String userName,
                        String password) {

        String errorMsg = null;
        // valid
        UserVo userVo = null;
        try {
            if (StringUtils.isBlank(userName)) {
                throw new SSOException("Please input username.");
            }
            if (StringUtils.isBlank(password)) {
                throw new SSOException("Please input password.");
            }
            userVo = client.getByUserName(userName);
//            String userStr = client.getByUserName(userName);
//            AjaxJson json = JSONObject.parseObject(userStr, AjaxJson.class);
            if (userVo == null) {
                throw new SSOException("username is invalid.");
            }
            password = PasswordUtil.encrypt(password, userVo.getUserName(), PasswordUtil.getStaticSalt());
            if (!userVo.getPassword().equals(password)) {
                throw new SSOException("password is invalid.");
            }
        } catch (SSOException e) {
            errorMsg = e.getMessage();
        }

        if (StringUtils.isNotBlank(errorMsg)) {
            redirectAttributes.addAttribute("errorMsg", errorMsg);

            redirectAttributes.addAttribute(Config.REDIRECT_URL, request.getParameter(Config.REDIRECT_URL));
            return "redirect:/login";
        }

        // login success
        SSOUser ssoUser = new SSOUser();
        ssoUser.setId(userVo.getId());
        ssoUser.setUserName(userVo.getUserName());

        String sessionId = UUID.randomUUID().toString();

        SSOLoginHelper.login(response, sessionId, ssoUser);

        // success redirect
        String redirectUrl = request.getParameter(Config.REDIRECT_URL);
        if (StringUtils.isNotBlank(redirectUrl)) {
            String redirectUrlFinal = redirectUrl + "?" + Config.SSO_SESSIONID + "=" + sessionId;
            return "redirect:" + redirectUrlFinal;
        } else {
            return "redirect:/";
        }
    }

    /**
     * Logout
     *
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = Config.SSO_LOGOUT)
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        // logout
        SSOLoginHelper.logout(request, response);

        redirectAttributes.addAttribute(Config.REDIRECT_URL, request.getParameter(Config.REDIRECT_URL));
        return "redirect:/login";
    }


}