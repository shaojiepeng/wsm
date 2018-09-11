package com.wsm.file.dto;

import java.util.List;

public class DirectoryTree {

    private Long id;

    private String name;

    private Long parentId;

    private String iconOpen = "/css/zTreeStyle/img/diy/11.png";

    private String iconClose = "/css/zTreeStyle/img/diy/10.png";

    private String icon = "/css/zTreeStyle/img/diy/10.png";

    private boolean open = true;

    private List<DirectoryTree> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<DirectoryTree> getChildren() {
        return children;
    }

    public void setChildren(List<DirectoryTree> children) {
        this.children = children;
    }

    public String getIconOpen() {
        return iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getIconClose() {
        return iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
