package com.wsm.admin.util;

import com.wsm.admin.dto.ResourceTree;
import com.wsm.admin.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceTreeUtil {
	
	private List<ResourceTree> resultNodes = new ArrayList<ResourceTree>();//树形结构排序之后list内容
	
    private List<ResourceTree> nodes = new ArrayList<ResourceTree>();; //传入list参数
    public ResourceTreeUtil(List<Resource> nodesList) {//通过构造函数初始化
        for (Resource n : nodesList){
        	ResourceTree treeGrid = new ResourceTree();
        	treeGrid.setId(n.getId());
        	treeGrid.setName(n.getName());
        	treeGrid.setIcon(n.getIcon());
        	treeGrid.setUrl(n.getUrl());
        	treeGrid.setResourceKey(n.getResourceKey());
        	treeGrid.setResourceType(n.getResourceType());
        	if (n.getParentId() != null){
        		treeGrid.setParentId(n.getParentId().getId());
        	}
        	nodes.add(treeGrid);
        }
    }
    
    public ResourceTreeUtil() {
	}

	/**
     * 构建树形结构list
     * @return 返回树形结构List列表
     */
    public List<ResourceTree> buildTree() {
        for (ResourceTree node : nodes) {
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
    private void build(ResourceTree node, int level) {
        List<ResourceTree> children = getChildren(node);
        if (!children.isEmpty()) {//如果存在子节点
        	node.setChildren(children);
//        	level++;
            for (ResourceTree child : children) {//将子节点遍历加入返回值中
//        		child.setLevel(level);
                build(child, child.getLevel());
            }
        }
    }
    
    /**
     * @param node
     * @return 返回
     */
    private List<ResourceTree> getChildren(ResourceTree node) {
        List<ResourceTree> children = new ArrayList<ResourceTree>();
        Long id = node.getId();
    	for (ResourceTree child : nodes) {
    		if (id.equals(child.getParentId())) {//如果id等于父id
    			children.add(child);//将该节点加入循环列表中
    		}
    	}
        return children;
    }
    
    public List<ResourceTree> buildTree(List<Resource> all, List<Resource> in){
    	for (Resource n : all){
        	ResourceTree treeGrid = new ResourceTree();
        	treeGrid.setId(n.getId());
        	treeGrid.setName(n.getName());
        	for(Resource nin : in){
        		if(nin.getId().equals(n.getId())){
        			treeGrid.setChecked(true);
        		}
        	}
        	if (n.getParentId() != null){
        		treeGrid.setParentId(n.getParentId().getId());
        	}
        	nodes.add(treeGrid);
        }
    	return buildTree();
    }

}
