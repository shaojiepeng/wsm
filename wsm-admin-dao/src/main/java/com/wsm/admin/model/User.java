package com.wsm.admin.model;

import com.wsm.common.model.BaseModel;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_user")
@Where(clause = "rec_status='A'")
public class User extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = -853504493430501564L;

	@Column(length = 50)
	private String userName;

	@Column(length = 100)
	private String password;

	@Column(length = 50)
	private String realName;

	@Column(length = 1)
	private byte status;
	
	private String avatar;
	
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch= FetchType.LAZY)
    @JoinTable(name = "tb_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns ={@JoinColumn(name = "role_id") })
    @Where(clause = "rec_status='A'")
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
