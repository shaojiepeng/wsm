package com.wsm.admin.model;

import com.wsm.common.model.BaseModel;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_role")
@Where(clause = "rec_status='A'")
public class Role extends BaseModel implements Serializable{

	private static final long serialVersionUID = -8093446477843493946L;
	
	@Column(length = 50)
	private String roleName;
	
	private String roleDesc;

	private String roleKey;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="tb_role_resource",joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="resource_id")})
    @OrderBy("sort ASC")
    private Set<Resource> resources;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
}
