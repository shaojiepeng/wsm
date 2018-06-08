package com.wsm.sso.config;


import com.wsm.sso.model.ResultT;

/**
 * conf
 *
 *  2018-04-02 19:18:04
 */
public class Config {

    /**
     * redirect url, for client
     */
    public static final String REDIRECT_URL = "redirect_url";

    /**
     * sso sessionid, between browser and sso-server
     */
    public static final String SSO_SESSIONID = "sso_sessionid";

    public static final String SSO_USER = "sso_user";



    /**
     * sso server address
     */
    public static final String SSO_SERVER = "sso_server";

    /**
     * login url
     */
    public static final String SSO_LOGIN = "/login";
    /**
     * logout url
     */
    public static final String SSO_LOGOUT = "/logout";



    /**
     * filter logout path
     */
    public static final String SSO_LOGOUT_PATH = "logoutPath";

    public static final ResultT<String> SSO_LOGIN_FAIL_RESULT = new ResultT(501, "sso not login.");


}
