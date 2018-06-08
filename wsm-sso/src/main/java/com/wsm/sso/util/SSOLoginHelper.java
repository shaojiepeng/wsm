package com.wsm.sso.util;

import com.wsm.sso.config.Config;
import com.wsm.sso.model.SSOUser;
import com.wsm.sso.storage.SSOLoginStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  2018-04-03
 */
public class SSOLoginHelper {


    /**
     * get sessionid by cookie (web)
     *
     * @param request
     * @return
     */
    public static String getSessionIdByCookie(HttpServletRequest request) {
        String cookieSessionId = CookieUtil.getValue(request, Config.SSO_SESSIONID);
        return cookieSessionId;
    }

    /**
     * set sessionid in cookie (web)ig
     *
     * @param response
     * @param sessionId
     */
    public static void setSessionIdInCookie(HttpServletResponse response, String sessionId) {
        if (sessionId!=null && sessionId.trim().length()>0) {
            CookieUtil.set(response, Config.SSO_SESSIONID, sessionId, false);
        }
    }

    /**
     * remove sessionId in cookie (web)
     *
     * @param request
     * @param response
     */
    public static void removeSessionIdInCookie(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, Config.SSO_SESSIONID);
    }

    /**
     * load cookie sessionid (app)
     *
     * @param request
     * @return
     */
    public static String cookieSessionIdGetByHeader(HttpServletRequest request) {
        String cookieSessionId = request.getHeader(Config.SSO_SESSIONID);
        return cookieSessionId;
    }

    /**
     * login check
     *
     * @param request
     * @return
     */
    public static SSOUser loginCheck(HttpServletRequest request){
        String cookieSessionId = getSessionIdByCookie(request);
        if (cookieSessionId!=null && cookieSessionId.trim().length()>0) {
            return loginCheck(cookieSessionId);
        }
        return null;
    }

    /**
     * login check
     *
     * @param sessionId
     * @return
     */
    public static SSOUser loginCheck(String  sessionId){
        if (sessionId!=null && sessionId.trim().length()>0) {
            SSOUser ssoUser = SSOLoginStore.get(sessionId);
            if (ssoUser != null) {
                return ssoUser;
            }
        }
        return null;
    }

    /**
     * client login (web)
     *
     * @param response
     * @param sessionId
     * @param ssoUser
     */
    public static void login(HttpServletResponse response,
                             String sessionId,
                             SSOUser ssoUser) {

        SSOLoginStore.put(sessionId, ssoUser);
        CookieUtil.set(response, Config.SSO_SESSIONID, sessionId, false);
    }

    /**
     * client login (app)
     *
     * @param sessionId
     * @param ssoUser
     */
    public static void login(String sessionId,
                             SSOUser ssoUser) {
        SSOLoginStore.put(sessionId, ssoUser);
    }


    /**
     * client logout (web)
     *
     * @param request
     * @param response
     */
    public static void logout(HttpServletRequest request,
                              HttpServletResponse response) {

        String cookieSessionId = getSessionIdByCookie(request);

        if (cookieSessionId != null) {
            SSOLoginStore.remove(cookieSessionId);
        }
        CookieUtil.remove(request, response, Config.SSO_SESSIONID);
    }

    /**
     * client logout (app)
     *
     * @param sessionId
     */
    public static void logout(String sessionId) {
        SSOLoginStore.remove(sessionId);
    }

}
