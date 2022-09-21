package org.data_structures_and_algorithms.array;

import java.util.Scanner;

/**
 * 循环队列
 */
public class CircleQueueArrayClass {

    private int maxSize; //最大容量
    private int front; //队列的首位值
    private int rear; //队列的末尾值的后一个索引
    private int[] queueArray; //队列数组

    public CircleQueueArrayClass(int size){

        this.maxSize=size;
        this.queueArray = new int[this.maxSize];
        this.front=0;
        this.rear=0;
    }

    /**
     * 队列实际值的末尾值的后一个索引右移,表示增加
     * rear的值的循环区间为 0到 maxSize-1，所以队列的值的索引循环区间为 queueArray[0]到 queueArray[maxSize-1]
     */
    public void addData(int data){

        if(!isFull()) {
            queueArray[this.rear] = data;
            //环形赋值
            this.rear=(rear + 1)% maxSize;
            return;
        }
        System.out.println("当前数组已满，无法添加数据");
    }

    /**
     * 队列实际值的首位值右移，表示消除队列的首位值
     */
    public int removeIndexData(){

        if(!isEmpty()) {
            int indexData = queueArray[front];
            //环形赋值
            front = (front + 1) % maxSize;
            return indexData;
        }
        throw new RuntimeException("当前数组为空,无数据展示");
    }

    /**
     * 获取队列的首位值
     */
    public int headData(){

        if(!isEmpty()) {
            return queueArray[front];
        }
        throw new RuntimeException("当前数组为空,无数据展示");
    }

    /**
     * 显示队列的所有值
     * 队列的值数量会满足: （  rear + maxSize - front ）% maxSize
     */
    public void showData(){

        if(!isEmpty()) {
            int count=(this.rear+this.maxSize-this.front) % maxSize;
            for (int i =this.front; count+this.front >i; i++) {
                System.out.printf("queueArray[%d]=%d\n",i % maxSize,queueArray[i % maxSize]);
            }
            return;
        }
        throw new RuntimeException("当前数组为空,无数据展示");
    }

    /**
     * 队列满员的情况
     * 因为队列的末尾值的后一个索引约定留空，所以队列的值最多为maxSize-1个
     * 因为removeIndexData()消除数组的值，当数组表示为空时，须能够添加数据
     */
    public boolean isFull(){

        return (rear + 1) % maxSize == front;
    }

    /**
     * 队列为空的情况
     */
    public boolean isEmpty(){

        return this.front==this.rear;
    }

    public static void main(String[] args) {

        CircleQueueArrayClass circleQueueArrayClass = new CircleQueueArrayClass(3);
        char input=' ';
        boolean open=true;
        Scanner scanner = new Scanner(System.in);
        while (open) {
            System.out.println("please select a choice");
            System.out.println("***********************");
            System.out.println("a:addData");
            System.out.println("r:removeIndexData");
            System.out.println("s:showData");
            System.out.println("h:headData");
            System.out.println("e:exit");
            //接收输入选择
            input=scanner.next().charAt(0);
            switch (input){
                case 'a':
                    System.out.println("please set a data");
                    circleQueueArrayClass.addData(scanner.nextInt());
                    break;
                case 'r':
                    try {
                        System.out.println("removeIndexData="+ circleQueueArrayClass.removeIndexData());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 's':
                    try {
                        circleQueueArrayClass.showData();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        System.out.println("headData="+ circleQueueArrayClass.headData());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    open=false;
                    break;
                default:
                    break;
            }
            System.out.println();
            System.out.println();
        }
    }


}
