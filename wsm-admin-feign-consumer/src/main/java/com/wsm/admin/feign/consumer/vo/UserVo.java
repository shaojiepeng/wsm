package com.wsm.admin.feign.consumer.vo;

import java.io.Serializable;

public class UserVo implements Serializable {

    private static final long serialVersionUID = -1517948241585405390L;

    public UserVo(){}

    private long id;

    private String userName;

    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
