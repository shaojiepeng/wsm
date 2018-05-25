package com.wsm.common.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
	@Column(length = 2)
	private String recStatus;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRecStatus() {
		return recStatus;
	}

	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
}
