package com.wsm.file.util;


import com.wsm.file.dto.DirectoryTree;
import com.wsm.file.model.Directory;

import java.util.ArrayList;
import java.util.List;

public class DirectoryTreeUtil {

    private List<DirectoryTree> resultNodes = new ArrayList<DirectoryTree>();//树形结构排序之后list内容

    private List<DirectoryTree> nodes = new ArrayList<DirectoryTree>();//传入list参数

    public DirectoryTreeUtil(List<Directory> nodesList) {//通过构造函数初始化
        for (Directory n : nodesList) {
            DirectoryTree treeGrid = new DirectoryTree();
            treeGrid.setId(n.getId());
            treeGrid.setName(n.getName());
            if (n.getParentId() != null) {
                treeGrid.setParentId(n.getParentId().getId());
            }
            nodes.add(treeGrid);
        }
    }

    public DirectoryTreeUtil() {
    }

    /**
     * 构建树形结构list
     *
     * @return 返回树形结构List列表
     */
    public List<DirectoryTree> buildTree() {
        for (DirectoryTree node : nodes) {
            Long id = node.getParentId();
            if (node.getParentId() == null) {//通过循环一级节点 就可以通过递归获取二级以下节点
                resultNodes.add(node);//添加一级节点
                build(node);//递归获取二级、三级、。。。节点
            }
        }
        return resultNodes;
    }

    /**
     * 递归循环子节点
     *
     * @param node 当前节点
     */
    private void build(DirectoryTree node) {
        List<DirectoryTree> children = getChildren(node);
        if (!children.isEmpty()) {//如果存在子节点
            node.setChildren(children);
            for (DirectoryTree child : children) {//将子节点遍历加入返回值中
                build(child);
            }
        }
    }

    /**
     * @param node
     * @return 返回
     */
    private List<DirectoryTree> getChildren(DirectoryTree node) {
        List<DirectoryTree> children = new ArrayList<DirectoryTree>();
        Long id = node.getId();
        for (DirectoryTree child : nodes) {
            if (id.equals(child.getParentId())) {//如果id等于父id
                children.add(child);//将该节点加入循环列表中
            }
        }
        return children;
    }

    /*public List<DirectoryTree> buildTree(List<Directory> all, List<Directory> in) {
        for (Directory n : all) {
            DirectoryTree treeGrid = new DirectoryTree();
            treeGrid.setId(n.getId());
            treeGrid.setName(n.getName());
            for (Directory nin : in) {
                if (nin.getId().equals(n.getId())) {
                    treeGrid.setChecked(true);
                }
            }
            if (n.getParentId() != null) {
                treeGrid.setParentId(n.getParentId().getId());
            }
            nodes.add(treeGrid);
        }
        return buildTree();
    }*/

}
