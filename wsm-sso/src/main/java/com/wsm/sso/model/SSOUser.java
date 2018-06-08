package com.wsm.sso.model;

import java.io.Serializable;

/**
 * sso user
 *
 *  2018-04-02 19:59:49
 */
public class SSOUser implements Serializable {

    private static final long serialVersionUID = 42L;

    private Long id;

    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
