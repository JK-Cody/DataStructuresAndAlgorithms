package org.data_structures_and_algorithms.recusion;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 递归
 */
public class RecusionClass {

    public static void main(String[] args) {
//
//        System.out.println("***********  test 1 **********");
//        MazeMapClass mazeMap = new MazeMapClass();
//        mazeMap.mazeMap();

        System.out.println("***********  test 2 **********");
        EightQueensClass01 eightQueensClass01 = new EightQueensClass01();
        eightQueensClass01.eightQueens();

        System.out.println("解法数量："+eightQueensClass01.trueResultList.size());
        for (List<Integer> integers : eightQueensClass01.trueResultList) {
            System.out.println(integers.toString());
        }

        System.out.println("***********  test 3 **********");
        EightQueensClass02 eightQueensClass02 = new EightQueensClass02();
        eightQueensClass02.eightQueens(0);
        System.out.println(eightQueensClass02.count);
        System.out.println(eightQueensClass02.block);
    }
}


/**
 * 迷宫
 */
class MazeMapClass{

    /**
     * 从迷宫中获取最短终点路径问题
     */
    public void mazeMap() {
        //迷宫规格
        int[][] map = new int[8][7];
        map[2][0] = 5;
        //将最外层设定为墙
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == 0) {
                    map[i][j] = 1;
                }
                if(i==map.length-1){
                    map[i][j]=1;
                }
                if(j==0){
                    map[i][j]=1;
                }
                if(j==map[i].length-1){
                    map[i][j]=1;
                }
            }
        }
        //如果有障碍
        map[4][2]=1;
        map[5][2]=1;
        map[6][2]=1;
        System.out.print("********墙的图案**********");
        for (int[] value : map) {
            for (int j = 0; j < value.length; j++) {
                System.out.print(value[j]);
            }
        }
        System.out.println();

        //设置起点
        int beginIndex1=1;
        int beginIndex2=1;

        boolean result = this.findTargetIndex(map, beginIndex1, beginIndex2);
        System.out.println("********路径的图案**********");
        if(result) {
            for (int[] ints : map) {
                System.out.println();
                for (int anInt : ints) {
                    System.out.print(anInt);
                }
            }
        }
    }

    /**
     * 找到迷宫移动路径
     */
    public boolean findTargetIndex(int[][] map, int i,int j){   //起始位置在递归时改变
        //设置终点
        int targetIndex1=6;
        int targetIndex2=5;

        if(map[targetIndex1][targetIndex2] == 2){
            return true;
        } else {
            if (map[i][j] == 0){
                //按照 下 右 上 左
                map[i][j] = 2; //标记经过
                //通过递归寻找其他路径
                if(findTargetIndex(map,i+1,j)){
                    return true;
                }else if(findTargetIndex(map,i,j+1)){
                    return true;
                }else if(findTargetIndex(map,i-1,j)){
                    return true;
                }else if(findTargetIndex(map,i,j-1)){
                    return true;
                }else {
                    map[i][j] = 3;  //无法通过
                    return false;
                }
            }else {
                System.out.println("没找到迷宫移动路径");
                return false;
            }
        }
    }
}

/**
 * 八皇后,解法一
 */
class EightQueensClass02{

    //保存可用的解法数量
    static int count = 0;
    //保存不可用的解法数量
    static int block = 0;
    int[] array = new int[8];

    /**
     *  放置棋子
     */
    public void eightQueens(int n){
        //直到第八行时
        if(n == 8){
            count++;
            return;
        }
//        n表示第n+1个棋子，也表示第n+1行， i表示第i+1列
        for (int i =0; i < 8 ;i++){   //每一行都会逐列移动,从第一列到第八列
            array[n] = i;  //给八个列标记 0-7,每个列都会递归到第八行
            if(findBlockLocation(n)){
                //当可用位置时，递归到下一行
                eightQueens(n+1);
            }
        }
    }

    /**
     * 判断棋盘不可用位置
     */
    public boolean findBlockLocation(int n){

        block ++;
        for (int i = 0 ;i <n ; i++){
            //判断是否在同一列
            //判断是否在同一斜线,列和行的差值相等
            if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i]))
                return false;
        }
        return true;
    }

}

/**
 * 八皇后,解法二
 */
class EightQueensClass01{

    //保存findRigntWay()的选择的排列组合
    static List<List<Integer>> resultList = new ArrayList<>();
    //保存最后可用的解法
    static List<List<Integer>> trueResultList=new ArrayList<>();

    /**
     * 获取findRigntWay()的选择的排列组合
     */
    public List<List<Integer>> changeCircleOrderNo( int[] arr, List<Integer> list){

        List<Integer> temp = new ArrayList<>(list);  //保存上一循环的集合
        //达到一组的长度时
        if (arr.length == list.size()){
            resultList.add(temp);
        }
        //获取数组的元素
        for (int i=0;i<arr.length;i++){
            if (temp.contains(arr[i])){
                continue;
            }
            temp.add(arr[i]);
            //使用递归继续添加元素，直到达到一组的长度
            this.changeCircleOrderNo(arr,temp);
            //添加前面的元素后删除该元素，使得下一循环将后面的元素排前
            temp.remove(temp.size()-1);
        }
        return resultList;
    }

    /**
     * 设置棋盘不可用位置
     */
    public boolean findBlockLocation (List<Integer> wrongList,List<Integer> trueList,int i,int j) {

        //如果是不可用位置
        int location = i + (j-1) * 8; //值
        if (wrongList.contains(location)) {
            return false;
        }
        //保存可用位置（用于完成后输出）
        trueList.add(i);
        trueList.add(j);
        //计算乘积后放入不可移动位置的列表
        for (int a=1;i+a<=8;a++){
            wrongList.add(a+location);  //右方
            if(j>a) {
                wrongList.add(location - a * 7); //斜上方与点的差值为7
            }
            if(8-j>=a) {
                wrongList.add(a * 9 + location); //斜下方与点的差值为9
            }
        }
        wrongList.add(1000);
        return true;
    }

    /**
     * 递归获取可用位置
     */
    public boolean findRigntWay(List<Integer> wrongList, List<Integer> trueList,int i, List<Integer> orderList)  {

        if (this.findBlockLocation(wrongList,trueList,i,orderList.get(0))) {
            return true;

        } else if (this.findBlockLocation(wrongList,trueList,i,orderList.get(1))) {
            return true;

        }else if (this.findBlockLocation(wrongList,trueList,i,orderList.get(2))) {
            return true;

        }else if (this.findBlockLocation(wrongList,trueList,i,orderList.get(3))) {
            return true;

        }else if (this.findBlockLocation(wrongList,trueList,i,orderList.get(4))) {
            return true;

        }else if (this.findBlockLocation(wrongList,trueList,i,orderList.get(5))) {
            return true;

        }else if (this.findBlockLocation(wrongList,trueList,i,orderList.get(6))) {
            return true;

        }else if (this.findBlockLocation(wrongList,trueList,i,orderList.get(7))) {
            return true;

        } else{
//            System.out.println("*********死棋********");
            return false;
        }
    }

    /**
     * 深拷贝List
     */
    public List<Integer> hardCopyList(List<Integer> list) {

        List<Integer> newList = new ArrayList<>();
        CollectionUtils.addAll(newList, new Integer[list.size()]);
        Collections.copy(newList, list);
        return newList;
    }

    /**
     * 八皇后棋盘
     */
    public void eightQueens() {

        List<Integer> wrongList =new ArrayList<>();
        List<Integer> trueList =new ArrayList<>();
        int i=1; //水平向右第几
        //获取findRigntWay()的选择的排列组合
        List<Integer> list =new ArrayList<>();
        int[] arr = {1,2,3,4,5,6,7,8};  //排列组合总数为  count=8*7*6*5*4*3*2*1 =40320
        this.changeCircleOrderNo(arr, list);
        List<List<Integer>> resultList = this.resultList;

        for (int k=0;k<40320;k++) {
            //按照当前排列组合获取可用位置
            for (int b = 1; b < 8; b++) {    // 竖直向下第几
                for (int a = 1; a <= 8; a++) {
                    //第一列先设置不可用位置
                    findBlockLocation(wrongList, trueList, 1, a);
                    //计算后续列的可用位置
                    while (true) {
                        if (!this.findRigntWay(wrongList, trueList, i,resultList.get(k))) {
                            wrongList.clear();
                            trueList.clear();
                            i = 2;
                            break;
                        }
                        if (i > 7) {
//                            System.out.println("****获得一组可通行位置****");
                            //因为trueList后续清空，所有添加需要深拷贝
                            if(!trueResultList.contains(trueList)){
                                List<Integer> newList = this.hardCopyList(trueList);
                                trueResultList.add(newList);
                            }
//                            System.out.println("****不可通行位置****");
//                            System.out.println(wrongList);
                            trueList.clear();
                            wrongList.clear();
                            i = 2;
                            break;
                        }
                        i++;
                    }
                }
            }
        }
    }
}