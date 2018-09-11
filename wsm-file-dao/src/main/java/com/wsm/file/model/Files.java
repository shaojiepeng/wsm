package com.wsm.file.model;

import com.wsm.common.model.BaseModel;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_file")
@Where(clause = "rec_status='A'")
public class Files extends BaseModel implements Serializable {

    private static final long serialVersionUID = 8253863803401343891L;

    private String oldName;

    private String name;

    private String fileAddress;

    private String urlAddress;

    @Column(length = 32)
    private String extensions;

    @Column(length = 32)
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "directoryId")
    private Directory directory;

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }
}
