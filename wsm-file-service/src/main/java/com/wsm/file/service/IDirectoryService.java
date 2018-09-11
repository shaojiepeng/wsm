package com.wsm.file.service;

import com.wsm.common.service.IBaseService;
import com.wsm.file.dto.DirectoryTree;
import com.wsm.file.model.Directory;

import java.util.List;

public interface IDirectoryService extends IBaseService<Directory, Long> {

    public List<DirectoryTree> getTree() throws Exception;

    public void remove(String directoryId);
}
