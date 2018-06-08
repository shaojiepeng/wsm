package com.wsm.operation.dto;

import java.util.List;

public class NewsTypeTree {

    private Long id;

    private String name;

    private Long parentId;

    private boolean checked = false;

    private boolean spread = false;

    private String isHeader = "0";

    private String order = "1";

    private int level;

    private List<NewsTypeTree> children;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<NewsTypeTree> getChildren() {
        return children;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildren(List<NewsTypeTree> children) {
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

}
