package com.wsm.file.model;

import com.wsm.common.model.BaseModel;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_directory")
@Where(clause = "rec_status='A'")
public class Directory extends BaseModel implements Serializable {

    private static final long serialVersionUID = -2511706209964944064L;

    @Column(length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Directory parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getParentId() {
        return parentId;
    }

    public void setParentId(Directory parentId) {
        this.parentId = parentId;
    }
}
