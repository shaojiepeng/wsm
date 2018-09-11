package com.wsm.file.service.impl;

import com.wsm.common.dao.IBaseDao;
import com.wsm.common.service.impl.BaseServiceImpl;
import com.wsm.file.dao.IDirectoryDao;
import com.wsm.file.dto.DirectoryTree;
import com.wsm.file.model.Directory;
import com.wsm.file.model.Files;
import com.wsm.file.service.IDirectoryService;
import com.wsm.file.service.IFilesService;
import com.wsm.file.util.DirectoryTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service("directoryService")
@Transactional
public class DirectoryServiceImpl extends BaseServiceImpl<Directory, Long> implements IDirectoryService {

    @Autowired
    private IDirectoryDao directoryDao;
    @Autowired
    private IFilesService filesService;

    @Override
    public IBaseDao<Directory, Long> getBaseDao() {
        return this.directoryDao;
    }

    @Override
    public List<DirectoryTree> getTree() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<Directory> directories = directoryDao.findAll(sort);
        DirectoryTreeUtil tree = new DirectoryTreeUtil(directories);
        return tree.buildTree();
    }

    @Override
    public void remove(String directoryId) {
        Directory directory = find(Long.valueOf(directoryId));
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");

        //删除子目录
        List<Directory> childDirectory = findList(new Specification<Directory>(){
            @Override
            public Predicate toPredicate(Root<Directory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("parentId").as(Directory.class), directory));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, sort);
        if (childDirectory != null && childDirectory.size() > 0){
            for (Directory dir : childDirectory){
                dir.setRecStatus("I");
                update(dir);
            }
        }
        //删除该目录下的资源
        List<Files> childFiles = filesService.findList(new Specification<Files>(){
            @Override
            public Predicate toPredicate(Root<Files> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("directory").as(Directory.class), directory));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, sort);
        if (childFiles != null && childFiles.size() > 0){
            for (Files file : childFiles){
                file.setRecStatus("I");
                filesService.update(file);
            }
        }

        //删除该目录
        if (directory != null){
            directory.setRecStatus("I");
            update(directory);
        }
    }

}
