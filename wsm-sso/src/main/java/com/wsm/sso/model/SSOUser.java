package com.wsm.sso.model;

import java.io.Serializable;
import java.util.Set;

/**
 * sso user
 *
 *  2018-04-02 19:59:49
 */
public class SSOUser implements Serializable {

    private static final long serialVersionUID = 42L;

    private Long id;

    private String userName;

    private Set<String> permissionSet;

    private Set<String> roleSet;

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

    public Set<String> getPermissionSet() {
        return permissionSet;
    }

    public void setPermissionSet(Set<String> permissionSet) {
        this.permissionSet = permissionSet;
    }

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<String> roleSet) {
        this.roleSet = roleSet;
    }
}
