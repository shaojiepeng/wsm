package com.wsm.file.api;


import com.wsm.common.api.BaseController;
import com.wsm.common.util.AjaxJson;
import com.wsm.common.util.ConstantUtils;
import com.wsm.file.dto.DirectoryTree;
import com.wsm.file.model.Directory;
import com.wsm.file.model.Files;
import com.wsm.file.service.IDirectoryService;
import com.wsm.file.service.IFilesService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/file")
public class DirectoryFileController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryFileController.class);

    @Autowired
    private IDirectoryService directoryService;
    @Autowired
    private IFilesService filesService;
    @Resource
    private FastDFSClientWrapper fwc;

    @RequestMapping("/list")
    public String list(String directoryId, Model model) {
        try {
            List<DirectoryTree> listTree = directoryService.getTree();
            model.addAttribute("directoryTree", listTree);
            if (StringUtils.isEmpty(directoryId)){
                directoryId = "1";
            }

            Sort sort = new Sort(Sort.Direction.DESC, "createTime");
            Directory directory = directoryService.find(Long.valueOf(directoryId));
            List<Files> childFiles = filesService.findList(new Specification<Files>(){
                @Override
                public Predicate toPredicate(Root<Files> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    predicates.add(cb.equal(root.get("directory").as(Directory.class), directory));
                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            }, sort);
            model.addAttribute("files", childFiles);
            model.addAttribute("currentDirectoryId", directoryId);
            model.addAttribute("currentDirectoryName", directory.getName());
        } catch (Exception e) {
            LOGGER.error("系统异常", e);
        }
        return "/file";
    }

    @RequestMapping(value = {"/directory/add"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson add(String folderName, String parentId) {
        AjaxJson ajaxJson = null;
        try {
            Directory directory = new Directory();
            directory.setName(folderName);
            directory.setRecStatus("A");
            directory.setParentId(directoryService.find(Long.valueOf(parentId)));
            directoryService.save(directory);
            ajaxJson = AjaxJson.success(ConstantUtils.SUCCESS_MSG);
        } catch (Exception e) {
            LOGGER.error("系统异常", e);
            ajaxJson = AjaxJson.failure("系统异常：" + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/directory/edit"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson edit(String folderName, String directoryId) {
        AjaxJson ajaxJson = null;
        try {
            Directory directory = directoryService.find(Long.valueOf(directoryId));
            directory.setName(folderName);
            directoryService.update(directory);
            ajaxJson = AjaxJson.success(ConstantUtils.SUCCESS_MSG);
        } catch (Exception e) {
            LOGGER.error("系统异常", e);
            ajaxJson = AjaxJson.failure("系统异常：" + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = {"/directory/remove"}, method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson remove(String directoryId) {
        AjaxJson ajaxJson = null;
        try {
            if (!StringUtils.isEmpty(directoryId)){
                directoryService.remove(directoryId);
                ajaxJson = AjaxJson.success(ConstantUtils.SUCCESS_MSG);
            }else{
                ajaxJson = AjaxJson.failure("目录id不能为空");
            }
        } catch (Exception e) {
            LOGGER.error("系统异常", e);
            ajaxJson = AjaxJson.failure("系统异常：" + e);
        }
        return ajaxJson;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson upload(MultipartFile file) {

        String directoryId = request.getParameter("directoryId");
        return null;
    }
}
