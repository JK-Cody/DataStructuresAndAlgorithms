package org.data_structures_and_algorithms.binarytree;

/**
 * 二叉排序树
 */
public class BinarySortClass {

    BinarySortNode root;

    /**
     * 添加节点
     */
    public void add(BinarySortNode node) {

        if (root == null) {
            root = node;
            return;
        }
        root.add(node);
    }

    /**
     * 删除节点
     */
    public void deleteByValue(int searchValue) {

        BinarySortNode searchNode = new BinarySortNode(searchValue);
        this.delete(searchNode);
    }

    /**
     * 删除节点
     */
    public void delete(BinarySortNode searchNode) {

        if (searchNode == null) {
            System.out.println("null in searchNode");
            return;
        }
        if (root == null) {
            System.out.println("null in root");
            return;
        }else if(root.value==searchNode.value) {
            if (root.left == null && root.right == null) {
                root = null;
                System.out.println("node{"+searchNode.value+"} have been delete,and root is null");
                return;
            }
        }
        BinarySortNode parent;
        //目标节点是根节点时
        if(searchNode.value == root.value){
            //创建根节点相同值的父节点
            parent = new BinarySortNode(root.value);
            parent.left=root;
            boolean result = root.delete(searchNode, parent);
            if (result) {
                System.out.println("node{"+searchNode.value+"} have been delete");
                return;
            }
        //目标节点是根节点的子节点时
        }else {
            //根节点作为父节点
            parent=root;
            boolean result = root.delete(searchNode, parent);
            if (result) {
                System.out.println("node{"+searchNode.value+"} have been delete");
                return;
            }
        }
        System.out.println("without find out node{"+searchNode.value+"}");
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
        System.out.println("***********  test 1 **********");
        BinarySortClass tree = new BinarySortClass();
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        for (int j : arr) {
            tree.add(new BinarySortNode(j));
        }
        tree.infixOrder();

        System.out.println("***********  test 2 **********");
//        int item = 2;
//        tree.add(new Node(item));
//        tree.infixOrder();
//        System.out.println("***********  test 3 **********");
//        item = 4;
//        tree.add(new Node(item));

        System.out.println("tree="+tree.root);
        System.out.println("***********  test 4 **********");
        BinarySortNode a=new BinarySortNode(7);
        tree.delete(a);
        tree.infixOrder();
        System.out.println("**************");
        System.out.println("tree="+tree.root);
        System.out.println("**************");
        tree.deleteByValue(3);
        tree.deleteByValue(10);
        System.out.println("tree="+tree.root);
    }
}

/**
 * 排序树节点
 */
class BinarySortNode {

    int value;
    BinarySortNode left;
    BinarySortNode right;

    public BinarySortNode(int value) {
        this.value = value;
    }

    /**
     * 添加节点
     */
    public void add(BinarySortNode node) {

        if (node == null) {
            System.out.println("节点数据不存在");
            return;
        }
        // 添加的值小于当前节点
        if (node.value < value) {
            // 有空位时直接放左边
            if (left == null) {
                left = node;
            } else {
                //递归向下
                left.add(node);
            }
        } else {
            // 有空位时直接放右走
            if (right == null) {
                right = node;
                //递归向下
            } else {
                right.add(node);
            }
        }
    }

    /**
     * 删除节点
     */
    public boolean delete(BinarySortNode searchNode,BinarySortNode parent) {
//小于时
        if (searchNode.value < value) {
            if (left == null) {
                return false;
            } else {
                //递归向下
                return left.delete(searchNode,this);
            }
//大于时
        }else if(searchNode.value > value){
            if (right == null) {
                return false;
            } else {
                //递归向下
                return right.delete(searchNode, this);
            }
//等于时
        }else {
//目标节点没有左右子节点时
            if (left == null && right == null) {
                //修改目标节点的值
                if(parent.left!=null && parent.left.value==searchNode.value){
                    parent.left = null;
                }else {
                    parent.right = null;
                }
                return true;
//目标节点有左右子节点时
            } else if (left != null && right != null) {
                //修改目标节点的值
                if(parent.left!=null && parent.left.value==searchNode.value){
                    //往目标节点的右子树的左节点里找到最接近值的节点
                    BinarySortNode near = parent.left.right;
                    BinarySortNode front = null;
                    while (near.left != null) {
                        //保存最小节点的父节点
                        front = near;
                        //保存最小节点
                        near = near.left;
                    }
                    parent.left.value = near.value;
                    //去除最小节点
                    if (front != null) {
                        front.left = null;
                    //目标节点是根节点时，front仍是null，去除右子树
                    }else {
                        parent.left.right=null;
                    }
                }else {
                    //往目标节点的右子树的左节点里找到最接近值的节点
                    BinarySortNode temp = parent.right.right;
                    BinarySortNode front = null;
                    while (temp.left != null) {
                        //保存最小节点的父节点
                        front = temp;
                        //保存最小节点
                        temp = temp.left;
                    }
                    parent.right.value = temp.value;
                    //去除最小节点
                    if (front != null) {
                        front.left = null;
                    }
                    //目标节点是根节点时，front仍是null，去除右子树
                    else {
                        parent.right.right=null;
                    }
                }
                return true;
//目标节点只有左节点时
            } else if (left != null) {
                //修改目标节点的值
                if(parent.left!=null && parent.left.value==searchNode.value){
                    //目标节点是根节点时，用根节点的左节点替代根节点
                    if(parent.value==this.value){
                        this.value= left.value;
                        this.right=left.right==null ? null:left.right;
                        this.left=left.left==null ? null:left.left;
                    //目标节点不是根节点时
                    }else {
                        parent.left = left;
                    }
                }else {
                    //目标节点是根节点时，用根节点的左节点替代根节点
                    if(parent.value==this.value){
                        this.value= left.value;
                        this.right=left.right==null ? null:left.right;
                        this.left=left.left==null ? null:left.left;
                    //目标节点不是根节点时
                    }else {
                        parent.right = left;
                    }
                }
                return true;
//目标节点只有右节点时
            } else {
                //修改目标节点的值
                if(parent.left!=null && parent.left.value==searchNode.value){
                    //目标节点是根节点时，用根节点的右节点替代根节点
                    if(parent.value==this.value) {
                        this.value = right.value;
                        this.left=right.left==null ? null:right.left;
                        this.right=right.right==null ? null:right.right;
                    }else {
                        parent.left = right;
                    }
                }else {
                    //目标节点是根节点时，用根节点的右节点替代根节点
                    if(parent.value==this.value) {
                        this.value = right.value;
                        this.left=right.left==null ? null:right.left;
                        this.right=right.right==null ? null:right.right;
                    }else {
                        parent.right = right;
                    }
                }
                return true;
            }
        }
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
