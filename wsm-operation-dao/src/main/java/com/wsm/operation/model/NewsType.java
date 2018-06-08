package com.wsm.operation.model;

import com.wsm.common.model.BaseModel;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_resource")
@Where(clause = "rec_status='A'")
public class NewsType extends BaseModel implements Serializable {

    private static final long serialVersionUID = 7964334182527868931L;

    @Column(length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private NewsType parentId;

    public String getName() {
        return name;
    }

    public NewsType getParentId() {
        return parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(NewsType parentId) {
        this.parentId = parentId;
    }
}
