package com.wsm.sso.filter;

import com.wsm.sso.config.Config;
import com.wsm.sso.model.SSOUser;
import com.wsm.sso.util.JacksonUtil;
import com.wsm.sso.util.SSOLoginHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * web sso filter
 *
 *  2018-04-03
 */
public class SSOFilter extends HttpServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(SSOFilter.class);

    private String ssoServer;
    private String logoutPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        ssoServer = filterConfig.getInitParameter(Config.SSO_SERVER);
        if (ssoServer!=null && ssoServer.trim().length()>0) {
            logoutPath = filterConfig.getInitParameter(Config.SSO_LOGOUT_PATH);
        }

        logger.info("SSOFilter init.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String servletPath = ((HttpServletRequest) request).getServletPath();
        String link = req.getRequestURL().toString();
        // logout filter
        if (logoutPath!=null
                && logoutPath.trim().length()>0
                && logoutPath.equals(servletPath)) {
            // remove cookie
            SSOLoginHelper.removeSessionIdInCookie(req, res);
            // redirect logout
            String logoutPageUrl = ssoServer.concat(Config.SSO_LOGOUT) + "?" + Config.REDIRECT_URL + "=" + request.getParameter(Config.REDIRECT_URL);
            res.sendRedirect(logoutPageUrl);
            return;
        }
        // login filter
        SSOUser ssoUser = null;
        // valid cookie user
        String cookieSessionId = SSOLoginHelper.getSessionIdByCookie(req);
        ssoUser = SSOLoginHelper.loginCheck(cookieSessionId);
        // valid param user, client login
        if (ssoUser == null) {
            // remove old cookie
            SSOLoginHelper.removeSessionIdInCookie(req, res);
            // set new cookie
            String paramSessionId = request.getParameter(Config.SSO_SESSIONID);
            if (paramSessionId != null) {
                ssoUser = SSOLoginHelper.loginCheck(paramSessionId);
                if (ssoUser != null) {
                    SSOLoginHelper.setSessionIdInCookie(res, paramSessionId);
                }
            }
        }
        // valid login fail
        if (ssoUser == null) {

            String header = req.getHeader("content-type");
            boolean isJson=  header!=null && header.contains("json");
            if (isJson) {
                // json msg
                res.setContentType("application/json;charset=utf-8");
                res.getWriter().println(JacksonUtil.writeValueAsString(Config.SSO_LOGIN_FAIL_RESULT));
                return;
            } else {
                // redirect logout
                String loginPageUrl = ssoServer.concat(Config.SSO_LOGIN)
                        + "?" + Config.REDIRECT_URL + "=" + link;
                res.sendRedirect(loginPageUrl);
                return;
            }
        }
        // ser sso user
        request.setAttribute(Config.SSO_USER, ssoUser);
        // already login, allow
        chain.doFilter(request, response);
        return;
    }

}
