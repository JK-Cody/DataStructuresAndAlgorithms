package org.data_structures_and_algorithms.binarytree;

/**
 * 平衡二叉树
 */
public class AVLBinarySearchTree {

    AVLNode root;

    public AVLNode getRoot() {
        return root;
    }

    /**
     * 添加节点
     */
    public void add(AVLNode node) {

        if (root == null) {
            root = node;
            return;
        }
        root.add(node);
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {

        if (root == null) {
            System.out.println("null in root");
            return;
        }
        root.infixOrder();
    }

    public static void main(String[] args) {

        AVLBinarySearchTree tree = new AVLBinarySearchTree();
        int[] arr = {10, 11, 7, 6, 8, 9};
        for (int i = 0; i < arr.length; i++) {
            tree.add(new AVLNode(arr[i]));
        }
        tree.infixOrder();
        System.out.println("树高度：" + tree.root.height());
        System.out.println("左树高度：" + tree.root.leftHeight());
        System.out.println("右树高度：" + tree.root.rightHeight());
        System.out.println("当前根节点：" + tree.root);
    }
}

/**
 * 排序树节点
 */
class AVLNode {

    int value;
    AVLNode left;
    AVLNode right;

    public AVLNode(int value){

        this.value=value;
    }

    /**
     * 获取树的高度
     */
    public int height() {

        return Math.max(
                (left == null ? 0 : left.height()),
                (right == null ? 0 : right.height())
        ) + 1;
    }

    /**
     * 计算左子树的高度
     */
    public int leftHeight() {

        if (left == null) {
            return 0;
        }
        return left.height();
    }

    /**
     * 计算右子树的高度
     */
    public int rightHeight() {

        if (right == null) {
            return 0;
        }
        return right.height();
    }

    /**
     * 添加节点
     */
    public void add(AVLNode node) {

        if (node == null) {
            return;
        }
        // 如果添加的值小于当前节点，则往左走
        if (node.value < value) {
            // 左节点为空，则直接挂在上面
            if (left == null) {
                left = node;
            } else {
                // 否则继续往下查找
                left.add(node);
            }
        } else {
            // 往右走
            if (right == null) {
                right = node;
            } else {
                right.add(node);
            }
        }
        //根节点左右字数高度差大于1时
        if(rightHeight()-leftHeight()>1){
            //当根节点的右子树的左子树高度大于右子树高度时
            if(left!=null && right.leftHeight() > right.rightHeight()){
                //右子树右旋
                right.rightRotate();
            }
            //左旋
            leftRotate();
        }else if(leftHeight()-rightHeight()>1){
            //当根节点的左子树的右子树高度大于左子树高度时
            if(left!=null && left.rightHeight() > left.leftHeight()){
                //左子树左旋
                left.leftRotate();
            }
            //右旋
            rightRotate();
        }
    }

    /**
     * 左旋右子树替代当前节点
     */
    public void leftRotate() {

        AVLNode newRoot = new AVLNode(this.value);
        //新节点的左子树设置为当前节点的左子树
        newRoot.left=left;
        //新节点的右子树设置为当前节点的右子树的左子树
        newRoot.right=right.left;
        //当前节点的值换为右子节点的值
        this.value=right.value;
        //当前节点的右子树设置为右子树的右子树
        this.right=right.right;
        //当前节点的左子树设置为新节点
        this.left=newRoot;
    }

    /**
     * 右旋左子树替代当前节点
     */
    public void rightRotate() {

        AVLNode newRoot = new AVLNode(this.value);
        //新节点的左子树设置为当前节点的右子树的左子树
        newRoot.right=right;
        //新节点的右子树设置为当前节点的右子树
        newRoot.left=left.right;
        //当前节点的值改为左子节点的值
        this.value=left.value;
        //当前节点的右子树设置为左子树的左子树
        this.left=left.left;
        //当前节点的右子树设置为新节点
        this.right=newRoot;
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {

        if (left != null) {
            left.infixOrder();
        }
        System.out.println(value);
        if (right != null) {
            right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value+
                ";left=" + left+
                ";right=" + right+
                '}';
    }
}