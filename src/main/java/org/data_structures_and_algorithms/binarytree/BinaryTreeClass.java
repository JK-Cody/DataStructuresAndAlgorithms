package org.data_structures_and_algorithms.binarytree;

import lombok.Data;

/**
 * 二叉树
 */
public class BinaryTreeClass {

    public BinaryTreeNode root;

    /**
     * 前序遍历
     */
    public void preOrder() {

        if (root == null) {
            System.out.println("二叉树为空");
            return;
        }
        root.preOrder();
    }

    /**
     * 前序查找
     */
    public BinaryTreeNode preOrderSearch(int targetId) {

        if (root == null) {
            System.out.println("二叉树为空");
            return null;
        }
        return root.preOrderSearch(targetId);
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (root == null) {
            System.out.println("二叉树为空");
            return;
        }
        root.infixOrder();
    }

    /**
     * 中序查找
     */
    public BinaryTreeNode infixOrderSearch(int targetId) {

        if (root == null) {
            System.out.println("二叉树为空");
            return null;
        }
        return root.infixOrderSearch(targetId);
    }

    /**
     * 后续遍历
     */
    public void postOrder() {
        if (root == null) {
            System.out.println("二叉树为空");
            return;
        }
        root.postOrder();
    }

    /**
     * 后续查找
     */
    public BinaryTreeNode postOrderSearch(int targetId) {

        if (root == null) {
            System.out.println("二叉树为空");
            return null;
        }
        return root.postOrderSearch(targetId);
    }

    /**
     * 删除节点
     */
    public void delete(int targetId) {

        if (root == null) {
            System.out.println("二叉树为空");
            return;
        }else{
            if(root.id==targetId){
                root=null;
                System.out.println("已找到并删除");
                return;
            }else {
               if(root.delete(targetId)){
                   System.out.println("已找到并删除");
                   return;
               }
            }
        }
        System.out.println("未找到");
    }

    public static void main(String[] args) {

        BinaryTreeNode n1 = new BinaryTreeNode(1, "宋江");
        BinaryTreeNode n2 = new BinaryTreeNode(2, "无用");
        BinaryTreeNode n3 = new BinaryTreeNode(3, "卢俊");
        BinaryTreeNode n4 = new BinaryTreeNode(4, "林冲");
        BinaryTreeNode n5 = new BinaryTreeNode(5, "关胜");
        n1.left = n2;
        n1.right = n3;
        n3.right = n4;
        n3.left = n5;
        BinaryTreeClass binaryTree = new BinaryTreeClass();
        binaryTree.root = n1;

        System.out.println("\n 前序遍历：");
        binaryTree.preOrder();
        System.out.println("\n 中序遍历：");
        binaryTree.infixOrder();
        System.out.println("\n 后序遍历：");
        binaryTree.postOrder();

        int id = 1;
        System.out.println("\n前序遍历查找 id=" + id);
        System.out.println(binaryTree.preOrderSearch(id));
        System.out.println("\n中序遍历查找 id=" + id);
        System.out.println(binaryTree.infixOrderSearch(id));
        System.out.println("\n后序遍历查找 id=" + id);
        System.out.println(binaryTree.postOrderSearch(id));

        binaryTree.delete(2);
        System.out.println("\n 前序遍历：");
        binaryTree.preOrder();
    }
}

/**
 * 二叉树节点
 */
@Data
class BinaryTreeNode {

    public int id;
    public String name;
    public BinaryTreeNode left;
    public BinaryTreeNode right;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        //先输出当前节点
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 前序查找
     */
    public BinaryTreeNode preOrderSearch(int targetId) {

        BinaryTreeNode binaryTreeNode =null;
        if(this.id==targetId){
            return this;
        }
        if (this.left != null) {
            binaryTreeNode = this.left.preOrderSearch(targetId);
            if(binaryTreeNode !=null) {
                return binaryTreeNode;
            }
        }
        if (this.right != null) {
            binaryTreeNode = this.right.preOrderSearch(targetId);
            if(binaryTreeNode !=null) {
                return binaryTreeNode;
            }
        }
        return binaryTreeNode;
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {

        if (this.left != null) {
            this.left.infixOrder();
        }
        //中段输出当前节点
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 中序查找
     */
    public BinaryTreeNode infixOrderSearch(int targetId) {

        BinaryTreeNode binaryTreeNode =null;
        if (this.left != null) {
            binaryTreeNode =this.left.infixOrderSearch(targetId);
            if(binaryTreeNode !=null) {
                return binaryTreeNode;
            }
        }
        if(this.id==targetId){
            return this;
        }
        if (this.right != null) {
            binaryTreeNode =this.right.infixOrderSearch(targetId);
            if(binaryTreeNode !=null) {
                return binaryTreeNode;
            }
        }
        return binaryTreeNode;
    }

    /**
     * 后序遍历
     */
    public void postOrder() {

        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        //后段输出当前节点
        System.out.println(this);
    }

    /**
     * 后序查找
     */
    public BinaryTreeNode postOrderSearch(int targetId) {

        BinaryTreeNode binaryTreeNode =null;
        if (this.left != null) {
            binaryTreeNode =this.left.postOrderSearch(targetId);
            if(binaryTreeNode !=null) {
                return binaryTreeNode;
            }
        }
        if (this.right != null) {
            binaryTreeNode =this.right.postOrderSearch(targetId);
            if(binaryTreeNode !=null) {
                return binaryTreeNode;
            }
        }
        if(this.id==targetId){
            return this;
        }
        return binaryTreeNode;
    }

    /**
     * 删除节点
     */
    public boolean delete(int targetId) {

        if (this.left != null) {
            if(this.left.id==targetId){
                this.left=null;
                return true;
            }
        }
        if (this.right != null) {
            if(this.right.id==targetId){
                this.right=null;
                return true;
            }
        }
        //从左节点递归查询
        if (this.left != null) {
            this.left.delete(targetId);
        }
        //从右节点递归查询
        if (this.right != null) {
            this.right.delete(targetId);
        }
        return false;
    }

}