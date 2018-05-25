package com.wsm.admin.model;

import com.wsm.common.model.BaseModel;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_resource")
@Where(clause = "rec_status='A'")
public class Resource extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -293343757324080501L;

	@Column(length = 50)
	private String name;

	private String description;

	private String url;

	@Column(length = 10)
	private String icon;
	
	private String resourceKey;

	@Column(columnDefinition = "enum('menu','button')")
	private String resourceType;
	
	@Column(length = 2)
	private Byte sort;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Resource parentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Resource getParentId() {
		return parentId;
	}

	public void setParentId(Resource parentId) {
		this.parentId = parentId;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public Byte getSort() {
		return sort;
	}

	public void setSort(Byte sort) {
		this.sort = sort;
	}

}
