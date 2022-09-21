package org.data_structures_and_algorithms.search;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 查找
 */
public class SearchClass {

    /**
     * 斐波那契查找
     * 将数组的长度索引嵌套进斐波那契数列，根据数列的规律逐渐接近目标索引
     */
    public static int fibSearch(int[] arr,int left,int right, int findVal){

        int index = 0;
        int mid = 0;

        //创建一个自增长的斐波那契数列
        int[] fib = fib(20);
        /* F[k] = F[k-1] + F[k-2] ==> F[k]-1 = (F[k -1] -1) + (F[k-2] -1) + 1
          只要arr长度为 F[k]-1，则可以将该表分成 长度为 F[k-1]-1 和 F[k-2]-1 两段
          因为数组的索引需被包含在数列里,斐波那契数列的最低长度需不小于数组的长度 */
        //当数列的最低长度小于数组的长度时
        while (right > fib[index] - 1) {
            index++;
        }
        //当数列的最低长度大于数组的长度时
        //将数组扩充到斐波那契数列的最低长度成为新数组,不足补0
        int[] temp = Arrays.copyOf(arr, fib[index]);
        // 将补0的部分的值统一修改为数组最大值
        for (int i = right + 1; i < temp.length; i++) {
            temp[i] = arr[right];
        }
        while (left <= right) {
            //数组长度为1时
            if (index == 0) {
                mid = left;
            //获取斐波那契数列的中间值,然后在新数组中匹配
            } else {
                //mid = low + F[k-1]-1
                mid = left + fib[index - 1] - 1;
            }
            /* 趋向左边部分 fib[index-1]-1, 将左边部分拆分得到 fib[index-1]-1=fib[index-2]-1+fib[index-3]-1 */
            //中间值 mid = left + fib[index-2]-1
            if (findVal < temp[mid]) {
                right = mid - 1;
                index--;
            /* 趋向右边部分 fib[index-2]-1,将右边部分拆分得到 fib[index-2]-1=fib[index-3]-1+fib[index-4]-1 */
            //中间值 mid = left + fib[index-3]-1
            } else if (findVal > temp[mid]) {
                left = mid + 1;
                index -= 2;
            } else {
                //由于数组嵌套在斐波那契数列中，而mid可能大于right，而且扩展长度时，后面连续一样的值，此时需要以首个值返回
                return Math.min(mid, right);
            }
        }
        return -1;
    }

    /**
     * 自增长的斐波那契数列
     */
    public static int[] fib(int maxSize){

        int[] fib = new int[20];
        fib[0] = 1;
        fib[1] = 1;
        for (int i = 2; i <maxSize ; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib;
    }

    /**
     * 二分查找——非递归形式查找
     */
    public static int BinarySearchWithoutRecusion(int[] arr,int findVal){

        int length = arr.length;
        if(findVal>arr[length-1] || findVal<arr[0] ) {
            System.out.println("查找的值不符合条件");
            return -1;
        }else if(findVal==arr[0]){
            System.out.println("查找的值的索引："+0);
            return 0;
        }else if(findVal==arr[length-1]){
            System.out.println("查找的值的索引："+(length-1));
            return length-1;
        }
        //获取中间值
        int mid=length/2;
        while (true){
            if(arr[mid] > findVal){
                mid=mid/2;
            }else if(arr[mid] < findVal){
                mid=(mid+length)/2;
            }else {
                System.out.println("查找的值的索引："+mid);
                return arr[mid];
            }
            if(mid==length-1){
                System.out.println("数组没有查找的值");
                return -1;
            }
        }
    }

    /**
     * 二分查找——插值查找
     */
    public static ArrayList<Integer> insertBinarySearch(int[] arr,int left,int right,int findVal){

        if(left > right || findVal>arr[right] || findVal<arr[left] ) {
            return null;
        }

        ArrayList<Integer> intList = new ArrayList<>();
        //使用中间索引的公式来设置值
        int mid = left+(left + right)*(findVal-arr[left]) / (arr[right]-arr[left]);

        if (findVal > arr[mid]){
            return insertBinarySearch(arr,mid+1,right,findVal);
        }else if(findVal < arr[mid]){
            return insertBinarySearch(arr,left,mid-1,findVal);
        }else {
            int temp = mid -1;
            //查询是否存在多个一样的值
            while (temp >= 0 && arr[temp] == findVal) {
                intList.add(temp);
                temp -= 1;
            }
            intList.add(mid);
            temp = mid +1;
            //查询是否存在多个一样的值
            while (temp <= (arr.length - 1) && arr[temp] == findVal) {
                intList.add(temp);
                temp += 1;
            }
            return intList;
        }
    }

    /**
     * 二分查找,要求为有序数组
     */
    public static  ArrayList<Integer> binarySearch (int[] arr,int left,int right,int findVal){

        ArrayList<Integer> intList = new ArrayList<>();
        int count=0;

        if(findVal>arr[right] || findVal<arr[left] ){
            return null;

        }else if(findVal==arr[left]){
            intList.add(left);
            count++;
            //查询是否存在多个一样的值
            while (findVal==arr[left+count]){
                intList.add(left+count);
                count++;
            }
            return intList;

        }else if(findVal==arr[right]){
            intList.add(right);
            count++;
            //查询是否存在多个一样的值
            while (findVal==arr[right-count]){
                intList.add(right-count);
                count++;
            }
            return intList;

        }else {
            int mid=(right+left)/2;
            if (arr[mid] < findVal){
                return binarySearch(arr,mid,right,findVal);
            }else if(arr[mid] > findVal){
                return binarySearch(arr,left,mid-1,findVal);
            }else{
                intList.add(mid);
                count++;
                //查询是否存在多个一样的值
                while (findVal==arr[mid+count]){
                    intList.add(mid+count);
                    count++;
                }
                while (findVal==arr[mid-count]){
                    intList.add(mid-count);
                    count++;
                }
                return intList;
            }
        }
    }

    /**
     * 线性查找
     */
    public static int seqsearch(int[] arr,int values){

        for (int i =0; i<arr.length;i++){
            if (arr[i] == values){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {

        System.out.println("***********  test 1 **********");
        int[] arr1 = {3,9,-1,10,-2};
        int seqsearch = seqsearch(arr1, -1);
        System.out.println(seqsearch);

        System.out.println("***********  test 2 **********");
        int[] arr2 = {-5,-4,-1,10,16,16,22,116,117};
        ArrayList<Integer> binarySearch = binarySearch(arr2, 0, arr2.length - 1, 16);
        System.out.println(binarySearch);

        System.out.println("***********  test 3 **********");
        int[] arr3 = {-5,-3,-1,1,8,16,22,110,116,116,117};
        ArrayList<Integer> insertBinarySearch = insertBinarySearch(arr3, 0, arr2.length - 1, 116);
        System.out.println(insertBinarySearch);

        System.out.println("***********  test 4 **********");
        int[] arr4 = {-4,1,8,16,22,110,116,116,117};
        int i = fibSearch(arr4, 0, arr2.length - 1, 116);
        System.out.println(i);

        int[] arr = new int[]{1, 3, 8, 10, 11, 67, 100};
        int target = 100;
        BinarySearchWithoutRecusion(arr,target);
    }
}
