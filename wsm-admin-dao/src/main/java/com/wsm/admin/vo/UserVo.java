package com.wsm.admin.vo;

import com.wsm.admin.model.Role;
import com.wsm.common.model.BaseModel;

import java.io.Serializable;
import java.util.Set;

public class UserVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1517948241585405390L;

    public UserVo(){}

    private String userName;

    private String password;

    private String realName;

    private byte status;

    private String avatar;

    private Set<Role> roles;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
