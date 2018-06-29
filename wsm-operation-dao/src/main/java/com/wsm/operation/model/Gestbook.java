package com.wsm.operation.model;

import com.wsm.common.model.BaseModel;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 留言表
 */
@Entity
@Table(name = "tb_gestbook")
@Where(clause = "rec_status='A'")
public class Gestbook extends BaseModel implements Serializable {

    private static final long serialVersionUID = 8749498332847760686L;

    @Column(length = 20)
    private String name;

    @Column(length = 11)
    private String phoneNum;

    @Column(length = 30)
    private String email;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
