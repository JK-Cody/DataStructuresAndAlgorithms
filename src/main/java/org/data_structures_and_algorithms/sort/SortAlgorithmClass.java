package org.data_structures_and_algorithms.sort;

import java.util.Arrays;

/**
 * 排序
 */
public class SortAlgorithmClass {

    /**
     * 堆排序
     */
    public static void heapSort(int[] arr){
        //将数组视为完全二叉树，从最后一个非叶子节点开始,其为 arr.length/2-1
        //每轮移向上一层的非叶子节点
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            //将非叶子节点调整为大顶堆，当前节点须大于或等于左右节点
            getIntoHeap(arr, i, arr.length);
        }
        // 从末尾元素开始与首位元素交换
        int temp = 0;
        for (int j = arr.length - 1; j > 0; j--) {
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            // 每轮将交换后的首位节点调整为大顶堆，须大于或等于左右节点
            getIntoHeap(arr, 0, j);
        }
    }

    /**
     * 堆排序--数组转换为大顶堆
     */
    private static void getIntoHeap(int[] arr, int i, int length) {

        int temp = arr[i];
        // 比较当前节点的左节点是否大于右节点
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                k++; // 跳到右节点
            }
            // 赋最大的值给当前节点
            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k;
            } else {
                break;
            }
        }
        //将当前节点的值赋值给左或右节点
        arr[i] = temp;
    }

    /**
     * 基数排序
     */
    public static void radixSort(int[] arr){

        int[][] bucket = new int[20][20];
        int[] bucketElementCounts = new int[20];
        int digit =1;
        int max = arr[0];
        //确定最大值的位数,从而直到循环几轮
        for(int i = 0; i<arr.length;i++){
            if (arr[i]>max){
                max = arr[i];
            }
        }
        //转换为字符串
        int maxLength = (max + "").length();
        //每一轮按照位数对于保存到新数组
        for (int i = 0;i<maxLength;i++) {
            for (int j = 0; j < arr.length; j++) {
                //获取位数的值
                int digitOfElement = arr[j] / digit % 10;
                //区分负数和正数的范围
                digitOfElement += 10;
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                //元素数量保存在bucketElementCounts
                bucketElementCounts[digitOfElement]++;
            }
            int index = 0;
            //将新数组的元素保存到原数组
            for (int k = 0; k < bucket.length; k++) {
                //要求元素值不为零
                if (bucketElementCounts[k] != 0) {
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        arr[index] = bucket[k][l];
                        index++;
                    }
                }
                //清零
                bucketElementCounts[k] =0;
            }
            digit *= 10;
        }
    }

    /**
     * 归并排序——分区后合元素
     */
    public static void mergeSortDivision(int[] arr,int left,int right,int[] temp) {

        if (left < right) {
            int mid = (left + right) / 2;
            //每轮的分区的数量不断减少，从一半元素一分区直到为一个元素一分区
            //先切分左分区元素
            mergeSortDivision(arr, left, mid, temp);
            //再切分右分区元素
            mergeSortDivision(arr, mid + 1, right, temp);
            //每一轮每个分区对元素重新排列顺序,到一半元素一分区时，左右各按顺序排列
            // 最后一轮时，原数组再按顺序添加左右分区的元素
            mergeSort(arr, left, mid, right, temp);
        }
    }

    /**
     * 归并排序——合元素
     */
    public static void mergeSort(int[] arr ,int left,int mid,int right,int[] temp){

        int indexLeft = left;
        int index  = 0;
        int indexMid = mid +1;
        //满足 左边<中间<右边时，进行排序
        while( indexLeft <= mid && indexMid <= right) {
            // 保存较小值
            if(arr[indexLeft] <= arr[indexMid]) {
                temp[index] = arr[indexLeft];
                index++;
                indexLeft++;
            } else {
                temp[index] = arr[indexMid];
                index ++;
                indexMid ++;
            }
        }
        //可能有一边的元素没有经过排序，此时将剩余元素添加到新数组
        while(indexLeft <= mid){
            temp[index] = arr[indexLeft];
            index ++;
            indexLeft ++;
        }
        while( indexMid <= right){
            temp[index] = arr[indexMid];
            index ++;
            indexMid++;
        }
        //将新数组的所有元素复制到原数组
        index = 0;
        int tempLeft = left;
        while(tempLeft <= right){
            arr[tempLeft] = temp[index];
            index ++;
            tempLeft ++;
        }
    }

    /**
     * 快速排序
     */
    public static void quickSort (int[] arr,int left,int right){

        int l=left;
        int r=right;
        int temp=0;
        //设定中间值
        int index=arr[(left+right)/2];

        while (l<r){
            while (arr[l]<index ){
                l++;
            }
            while (arr[r]>index ){
                r--;
            }
            //左右调换
            temp=arr[l];
            arr[l]=arr[r];
            arr[r]=temp;

            if(l>=r){
                break;
            }
            //到达中间值时
            if(arr[l]==index){
                r--;
            }
            if(arr[r]==index){
                l++;
            }
        }
        //错开
        if(l==r){
            l++;
            r--;
        }
        //对中间值的左右递归进行比较
        if(left<r){
            quickSort(arr, left, r);
        }
        if(l<right){
            quickSort(arr, l, right);
        }
    }

    /**
     * 希尔排序2
     */
    public static void shellSort02 (int[] arr){

        int length=arr.length/2;
        int temp=0;
        //每一次都对半分,执行每一次的步长
        while (length>=1){
            //对半位置和最右位置比较大小，下一轮两边递增比较
            for (int i = length; i<arr.length;i++){
                int j = i;
                temp = arr[j];
                //需要调换位置
                if (arr[j] < arr[j-length]){
                    //当步长为1时，使用插入排序当前元素
                    while( j- length >=0 && temp < arr[j-length]) {
                        arr[j] = arr[j-length];
                        j-= length;
                    }
                    arr[j] = temp;
                }
            }
            //步长缩半
            length=length/2;
        }
    }

    /**
     * 希尔排序1
     */
    public static void shellSort01 (int[] arr){

        int length=arr.length/2;
        int temp=0;
        //执行每一次的步长
        while (length>=1){
            //对半位置和最右位置比较大小，下一轮两边递增比较
            for (int i = length;i < arr.length; i++){
                for (int j = i-length; j>= 0; j-= length){
                    //需要调换位置
                    if (arr[j] > arr[j+length]){
                        temp = arr[j];
                        arr[j] = arr[j+length];
                        arr[j+length] = temp;
                    }
                }
            }
            //步长缩半
            length=length/2;
        }
    }

    /**
     * 插入排序
     */
    public static void insertSort (int[] arr){

        int index=0;
        int temp=0;
        for (int i = 1; i< arr.length ;i++){
            temp=arr[i];
            index=i-1;
            //后面的元素大于当前元素
            while (index>=0 && temp < arr[index]){
                arr[index+1]=arr[index];
                index--;
            }
            //将上一个元素设为当前元素
            arr[index+1]=temp;
        }
    }

    /**
     * 选择排序
     */
    public static void selectSort(int[] arr){

        int minIndex= 0;
        int temp=0;
        for (int i = 0; i< arr.length ;i++){
            //保存第一个索引
            minIndex=i;
            //查找最小值的索引
            for (int j = i+1; j <arr.length ; j++){
                if(arr[j] < arr[minIndex]){
                    minIndex =j ;
                }
            }
            if(minIndex!=i) {
                temp=arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    /**
     * 冒泡排序
     */
    public static void bubbleSort(int[] arr){

        int temp= 0;
        //多轮排序，让索引整体从小到大
        for (int i = 0; i< arr.length - 1 ;i++){
            boolean flag = false;
            //每一轮比较出一个最大值
            for (int j = 0; j <arr.length -1 -i ; j++){
                if(arr[j]>arr[j+1]){
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            // 如果本次没有经过排序，不用再排序
            if (!flag) {
                break;
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("***********  test 1 **********");
        int[] arr1 = {9,3,-1,10,-2};
        bubbleSort(arr1);
        System.out.println(Arrays.toString(arr1));

        System.out.println("***********  test 2 **********");
        int[] arr2 = {5,-4,3,9,-1,10,-2,16,7};
        selectSort(arr2);
        System.out.println(Arrays.toString(arr2));

        System.out.println("***********  test 3 **********");
        int[] arr3 = {1,-2,5,-4,3,9,-1,10,-8,16,7};
        insertSort(arr3);
        System.out.println(Arrays.toString(arr3));

        System.out.println("***********  test 4 **********");
        int[] arr4 = {1,-2,5,-4,-7,-9,11,2};
        shellSort01(arr4);
        System.out.println(Arrays.toString(arr4));

        System.out.println("***********  test 5 **********");
        int[] arr5 = {1,-2,5,-4,6,-9,8,2};
        shellSort02(arr5);
        System.out.println(Arrays.toString(arr5));

        System.out.println("***********  test 6 **********");
        int[] arr6 = {-4,6,-9,8,2,3,-8,7};
        quickSort(arr6,0,arr6.length-1);
        System.out.println(Arrays.toString(arr6));

        System.out.println("***********  test 7 **********");
        int[] arr7 = {-4,-2,6,-9,8,2,3,-8,7};
        int[] temp = new int[arr7.length];
        mergeSortDivision(arr7,0, arr7.length-1,temp);
        System.out.println(Arrays.toString(arr7));
//
//        System.out.println("***********  test 8 **********");
//        int[] arr8 = {-4,-2,6,-9,8,2,3,-8,7};
//        radixSort(arr8);
//        System.out.println(Arrays.toString(arr8));
//
//        System.out.println("***********  test 9 **********");
//        int[] arr9 = {-4,-2,6,-9,8,2,3,-8,7};
//        heapSort(arr9);
//        System.out.println(Arrays.toString(arr9));
    }


}
