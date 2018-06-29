package com.wsm.operation.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.wsm.common.model.BaseModel;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

public class NewsVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 5098640360010622251L;

    private String title;

    private String intro;

    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date releaseDate;

    private String content;

    private String newsTypeName;

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

    public String getNewsTypeName() {
        return newsTypeName;
    }

    public void setNewsTypeName(String newsTypeName) {
        this.newsTypeName = newsTypeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
