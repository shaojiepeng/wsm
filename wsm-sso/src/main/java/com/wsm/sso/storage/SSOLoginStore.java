package com.wsm.sso.storage;


import com.wsm.sso.config.Config;
import com.wsm.sso.model.SSOUser;
import com.wsm.sso.util.JedisUtil;

/**
 * local login store
 *
 *  2018-04-02 20:03:11
 */
public class SSOLoginStore {

    /**
     * get
     *
     * @param sessionId
     * @return
     */
    public static SSOUser get(String sessionId) {

        String redisKey = redisKey(sessionId);
        Object objectValue = JedisUtil.getObjectValue(redisKey);
        if (objectValue != null) {
            SSOUser ssoUser = (SSOUser) objectValue;
            return ssoUser;
        }
        return null;
    }

    /**
     * remove
     *
     * @param sessionId
     */
    public static void remove(String sessionId) {
        String redisKey = redisKey(sessionId);
        JedisUtil.del(redisKey);
    }

    /**
     * put
     *
     * @param sessionId
     * @param ssoUser
     */
    public static void put(String sessionId, SSOUser ssoUser) {
        String redisKey = redisKey(sessionId);
        JedisUtil.setObjectValue(redisKey, ssoUser);
    }

    private static String redisKey(String sessionId){
        return Config.SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
