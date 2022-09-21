package org.data_structures_and_algorithms.binarytree;

import lombok.Data;

/**
 * 线索化二叉树
 */
public class ThreadedBinaryTreeClass {

    public ThreadedBinaryTreeNode root;

    public ThreadedBinaryTreeNode front;

    /**
     * 前序遍历
     */
    public void getPreOrder() {

        ThreadedBinaryTreeNode threadedBinaryTreeNode = root;

        while (threadedBinaryTreeNode != null) {
            System.out.println(threadedBinaryTreeNode);
            while (threadedBinaryTreeNode.getFrontType() == 0) {
                threadedBinaryTreeNode = threadedBinaryTreeNode.getLeft();
                System.out.println(threadedBinaryTreeNode);
            }
            while (threadedBinaryTreeNode.getNextType() == 1) {
                threadedBinaryTreeNode = threadedBinaryTreeNode.getRight();
                System.out.println(threadedBinaryTreeNode);
            }
            threadedBinaryTreeNode = threadedBinaryTreeNode.getRight();
        }
    }

    /**
     * 前序赋值
     */
    public void pre(ThreadedBinaryTreeNode threadedBinaryTreeNode) {

        if (threadedBinaryTreeNode == null) {
            return;
        }
        // 左节点为空，此为叶子节点，设置前驱节点指向上一个遍历的节点
        if (threadedBinaryTreeNode.getLeft() == null) {
            threadedBinaryTreeNode.setLeft(front); //刚开始指向null
            threadedBinaryTreeNode.setFrontType(1);
        }
        //当前节点设置为前一个的后继节点
        // 右节点为空时，此为叶子节点，设置后端节点指向下一个遍历的节点
        if (front != null && front.getRight() == null) {
            front.setRight(threadedBinaryTreeNode);
            front.setNextType(1);
        }
        front = threadedBinaryTreeNode;
        // 递归获取下一层左节点
        if (threadedBinaryTreeNode.getFrontType() == 0) {
            pre(threadedBinaryTreeNode.getLeft());
        }
        // 递归获取取下一层右节点
        if (threadedBinaryTreeNode.getNextType() == 0) {
            pre(threadedBinaryTreeNode.getRight());
        }
    }

    /**
     * 中序遍历
     */
    public void getInfixOrder() {

        ThreadedBinaryTreeNode threadedBinaryTreeNode = root;

        while (threadedBinaryTreeNode != null) {
            while (threadedBinaryTreeNode.getFrontType() == 0) {
                threadedBinaryTreeNode = threadedBinaryTreeNode.getLeft();
            }
            System.out.println(threadedBinaryTreeNode);
            while (threadedBinaryTreeNode.getNextType() == 1) {
                threadedBinaryTreeNode = threadedBinaryTreeNode.getRight();
                System.out.println(threadedBinaryTreeNode);
            }
            threadedBinaryTreeNode = threadedBinaryTreeNode.getRight();
        }
    }

    /**
     * 中序赋值
     */
    public void infix(ThreadedBinaryTreeNode threadedBinaryTreeNode) {

        if (threadedBinaryTreeNode == null) {
            return;
        }
        // 递归获取最后一层左节点
        infix(threadedBinaryTreeNode.getLeft());
        // 左节点为空，此为叶子节点，设置前驱节点指向上一个遍历的节点
        if (threadedBinaryTreeNode.getLeft() == null) {
            threadedBinaryTreeNode.setLeft(front);//刚开始指向null
            threadedBinaryTreeNode.setFrontType(1);
        }
        //当前节点设置为前一个的后继节点
        // 右节点为空时，此为叶子节点，设置后端节点指向下一个遍历的节点
        if (front != null && front.getRight() == null) {
            front.setRight(threadedBinaryTreeNode);
            front.setNextType(1);
        }
        // 保存当前节点为临时前节点
        front = threadedBinaryTreeNode;
        // 递归获取倒数第二层的右节点
        infix(threadedBinaryTreeNode.getRight());
    }

    public static void main(String[] args) {

        ThreadedBinaryTreeNode n1 = new ThreadedBinaryTreeNode(1, "宋江");
        ThreadedBinaryTreeNode n2 = new ThreadedBinaryTreeNode(2, "无用");
        ThreadedBinaryTreeNode n3 = new ThreadedBinaryTreeNode(3, "卢俊");
        ThreadedBinaryTreeNode n4 = new ThreadedBinaryTreeNode(4, "林冲2");
        ThreadedBinaryTreeNode n5 = new ThreadedBinaryTreeNode(5, "林冲3");
        ThreadedBinaryTreeNode n6 = new ThreadedBinaryTreeNode(6, "林冲4");
        n1.setLeft(n2);
        n1.setRight(n3);
        n2.setLeft(n4);
        n2.setRight(n5);
        n3.setLeft(n6);
        ThreadedBinaryTreeClass tree1 = new ThreadedBinaryTreeClass();
        tree1.root = n1;
        tree1.pre(n1);
        ThreadedBinaryTreeNode l = n2.getLeft();
        ThreadedBinaryTreeNode r = n2.getRight();
        System.out.println("前驱节点：" + l.getId());
        System.out.println("后继节点：" + r.getId());
        tree1.getPreOrder();

        ThreadedBinaryTreeNode n7 = new ThreadedBinaryTreeNode(7, "宋江");
        ThreadedBinaryTreeNode n8 = new ThreadedBinaryTreeNode(8, "无用");
        ThreadedBinaryTreeNode n9 = new ThreadedBinaryTreeNode(9, "卢俊");
        ThreadedBinaryTreeNode n10 = new ThreadedBinaryTreeNode(10, "林冲2");
        ThreadedBinaryTreeNode n11 = new ThreadedBinaryTreeNode(11, "林冲3");
        ThreadedBinaryTreeNode n12 = new ThreadedBinaryTreeNode(12, "林冲4");
        n7.setLeft(n8);
        n7.setRight(n9);
        n8.setLeft(n10);
        n8.setRight(n11);
        n9.setLeft(n12);
        ThreadedBinaryTreeClass tree2 = new ThreadedBinaryTreeClass();
        tree2.root = n7;
        tree2.infix(n7);
        ThreadedBinaryTreeNode left = n8.getLeft();
        ThreadedBinaryTreeNode right = n8.getRight();
        System.out.println("前驱节点：" + left.getId());
        System.out.println("后继节点：" + right.getId());
        tree2.getInfixOrder();
    }
}

/**
 * 二叉树节点
 */
@Data
class ThreadedBinaryTreeNode {

    private int id;
    private String name;
    private ThreadedBinaryTreeNode left;
    private ThreadedBinaryTreeNode right;
    //1表示前驱节点 ；0表示左子树节点
    private int frontType;
    //1表示后端节点；0表示左子树节点
    private int nextType;

    public ThreadedBinaryTreeNode() {
    }

    public ThreadedBinaryTreeNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}