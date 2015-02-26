package com.netcracker.edu.java.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * un1acker
 * 26.02.2015
 */
public class TreeNodeImpl implements TreeNode {

    private TreeNode parent;
    private List<TreeNode> children;
    private Object data;
    private boolean expanded;

    public TreeNodeImpl() {
        this.parent = null;
        this.children = null;
        this.data = null;
        this.expanded = false;
    }


    public TreeNodeImpl(Object data, TreeNode parent) {
        this.data = data;
        this.children = null;
        this.expanded = false;
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }

    }

    @Override
    public TreeNode getParent() {
        return this.parent;
    }

    @Override
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    @Override
    public TreeNode getRoot() {
        if (this.getParent() == null && this.isLeaf()) {
            return null;
        }
        if (this.getParent() == null) {
            return this;
        } else {
            return this.getParent().getRoot();
        }
    }

    @Override
    public boolean isLeaf() {
        return this.getChildCount() == 0;
    }

    @Override
    public int getChildCount() {
        return (this.children == null) ? 0 : this.children.size();
    }

    @Override
    public Iterator<TreeNode> getChildrenIterator() {
        return this.children.iterator();
    }

    @Override
    public void addChild(TreeNode child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        child.setParent(this);
        this.children.add(child);
    }

    @Override
    public boolean removeChild(TreeNode child) {
        if (this.children != null && this.children.contains(child)) {
            this.children.remove(child);
            child.setParent(null);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isExpanded() {
        return this.expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        if (!this.isLeaf()) {
            Iterator<TreeNode> iterator = this.getChildrenIterator();
            while (iterator.hasNext()) {
                iterator.next().setExpanded(expanded);
            }
        } else {
            return;
        }
    }

    @Override
    public Object getData() {
        return this.data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String getTreePath() {
        ArrayList<TreeNode> nodes = new ArrayList<>();
        TreeNode node = this;
        nodes.add(node);
        while (node.getParent() != null) {
            nodes.add(node.getParent());
            node = node.getParent();
        }

        StringBuilder treePath = new StringBuilder();
        for (int i = nodes.size() - 1; i >= 0; i--) {
            treePath.append(nodes.get(i).toString());
            if (i != 0) {
                treePath.append("->");
            }
        }
        return treePath.toString();
    }

    @Override
    public TreeNode findParent(Object data) {
        if (this.parent != null) {
            if ((this.getData() != null && this.getData().equals(data)) || (this.getData() == null && this.getData() == data)) {
                return this;
            } else {
                return this.getParent().findParent(data);
            }
        } else {
            return null;
        }
    }

    @Override
    public TreeNode findChild(Object data) {
        if (this.children != null) {
            Iterator<TreeNode> iterator = this.getChildrenIterator();
            while (iterator.hasNext()) {
                TreeNode nextNode = iterator.next();
                if ((nextNode.getData() != null && nextNode.getData().equals(data)) || (nextNode.getData() == null && nextNode.getData() == data)) {
                    return nextNode;
                }
            }
            while (iterator.hasNext()) {
                TreeNode nextNode = iterator.next();
                if (!nextNode.isLeaf()) {
                    return nextNode.findChild(data);
                }
            }
        }
        return null;
    }

    public String toString() {
        if (this.getData() == null) {
            return super.toString();
        } else {
            return this.getData().toString();
        }
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }

        TreeNode node = (TreeNode) o;
        return (this.data.equals(((TreeNode) o).getData())
                && this.parent.equals(((TreeNode) o).getParent()));

    }
}
