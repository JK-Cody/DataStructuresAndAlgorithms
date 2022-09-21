package org.data_structures_and_algorithms.binarytree;

/**
 * 顺序存储二叉树
 */
public class ArrayBinaryTreeClass {

    private int[] arr;

    public ArrayBinaryTreeClass(int[] arr) {
        this.arr = arr;
    }

    /**
     * 前序遍历
     */
    public void preOrder(int index) {

        if (arr == null || arr.length == 0) {
            System.out.println("数组为空");
            return;
        }
        System.out.println(arr[index]);
        //将数组转换为二叉树形式输出
     /* 第 n 个元素的 左子节点 为 2*n+1
        第 n 个元素的 右子节点 为 2*n+2
        第 n 个元素的 父节点 为 (n-1)/2 */
        int left = 2 * index + 1;
        if (left < arr.length) {
            preOrder(left);
        }
        int right = 2 * index + 2;
        if (right < arr.length) {
            preOrder(right);
        }
    }

    public static void main(String[] args) {

        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTreeClass tree = new ArrayBinaryTreeClass(arr);
        tree.preOrder(0);
    }
}
