package com.wsm.sso.filter;

import com.wsm.sso.config.Config;
import com.wsm.sso.model.ResultT;
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
 * app sso filter
 *
 *  2018-04-08 21:30:54
 */
public class SSOTokenFilter extends HttpServlet implements Filter {
    private static Logger logger = LoggerFactory.getLogger(SSOTokenFilter.class);

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

        String sessionid = SSOLoginHelper.cookieSessionIdGetByHeader(req);
        SSOUser ssoUser = SSOLoginHelper.loginCheck(sessionid);

        // logout filter
        if (logoutPath!=null
                && logoutPath.trim().length()>0
                && logoutPath.equals(servletPath)) {

            if (ssoUser != null) {
                SSOLoginHelper.logout(sessionid);
            }

            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println(JacksonUtil.writeValueAsString(new ResultT(ResultT.SUCCESS_CODE, null)));
            return;
        }

        // login filter
        if (ssoUser == null) {

            // response
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().println(JacksonUtil.writeValueAsString(Config.SSO_LOGIN_FAIL_RESULT));
            return;
        }

        // ser sso user
        request.setAttribute(Config.SSO_USER, ssoUser);


        // already login, allow
        chain.doFilter(request, response);
        return;
    }


}
