package com.wsm.admin.dto;

import java.util.List;

public class ResourceTree {

	private Long id;
	
	private String name;
	
	private Long parentId;
	
	private boolean checked = false;
	
	private boolean spread = false;
	
	private String isHeader = "0";
	
	private String url;
	
	private String icon;
	
	private String resourceKey;
	
	private String resourceType;
	
	private String order = "1";
	
	private List<ResourceTree> children;
	
	private int level;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<ResourceTree> getChildren() {
		return children;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChildren(List<ResourceTree> children) {
		this.children = children;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
		this.spread = checked;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}
	

	public String getIsHeader() {
		return isHeader;
	}

	public void setIsHeader(String isHeader) {
		this.isHeader = isHeader;
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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}
}
