package com.wsm.operation.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.wsm.common.model.BaseModel;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_news")
@Where(clause = "rec_status='A'")
public class News extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1658769933983990679L;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String intro;

    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date releaseDate;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsTypeId")
    private NewsType newsType;

    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
