package com.wsm.operation.util;

import com.wsm.operation.dto.NewsTypeTree;
import com.wsm.operation.model.NewsType;

import java.util.ArrayList;
import java.util.List;

public class NewsTypeTreeUtil {

    private List<NewsTypeTree> resultNodes = new ArrayList<NewsTypeTree>();//树形结构排序之后list内容

    private List<NewsTypeTree> nodes = new ArrayList<NewsTypeTree>();
    ; //传入list参数

    public NewsTypeTreeUtil(List<NewsType> nodesList) {//通过构造函数初始化
        for (NewsType n : nodesList) {
            NewsTypeTree treeGrid = new NewsTypeTree();
            treeGrid.setId(n.getId());
            treeGrid.setName(n.getName());
            if (n.getParentId() != null) {
                treeGrid.setParentId(n.getParentId().getId());
            }
            nodes.add(treeGrid);
        }
    }

    public NewsTypeTreeUtil() {
    }

    /**
     * 构建树形结构list
     *
     * @return 返回树形结构List列表
     */
    public List<NewsTypeTree> buildTree() {
        for (NewsTypeTree node : nodes) {
            Long id = node.getParentId();
            if (node.getParentId() == null) {//通过循环一级节点 就可以通过递归获取二级以下节点
                resultNodes.add(node);//添加一级节点
//                node.setLevel(1);
                build(node, node.getLevel());//递归获取二级、三级、。。。节点
            }
        }
        return resultNodes;
    }

    /**
     * 递归循环子节点
     *
     * @param node 当前节点
     */
    private void build(NewsTypeTree node, int level) {
        List<NewsTypeTree> children = getChildren(node);
        if (!children.isEmpty()) {//如果存在子节点
            node.setChildren(children);
//        	level++;
            for (NewsTypeTree child : children) {//将子节点遍历加入返回值中
//        		child.setLevel(level);
                build(child, child.getLevel());
            }
        }
    }

    /**
     * @param node
     * @return 返回
     */
    private List<NewsTypeTree> getChildren(NewsTypeTree node) {
        List<NewsTypeTree> children = new ArrayList<NewsTypeTree>();
        Long id = node.getId();
        for (NewsTypeTree child : nodes) {
            if (id.equals(child.getParentId())) {//如果id等于父id
                children.add(child);//将该节点加入循环列表中
            }
        }
        return children;
    }

    public List<NewsTypeTree> buildTree(List<NewsType> all, List<NewsType> in) {
        for (NewsType n : all) {
            NewsTypeTree treeGrid = new NewsTypeTree();
            treeGrid.setId(n.getId());
            treeGrid.setName(n.getName());
            for (NewsType nin : in) {
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
    }

}
