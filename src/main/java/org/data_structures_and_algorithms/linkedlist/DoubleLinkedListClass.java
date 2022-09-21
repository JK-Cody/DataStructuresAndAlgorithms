package org.data_structures_and_algorithms.linkedlist;

import java.util.Stack;

/**
 * 双链表
 */
public class DoubleLinkedListClass {
    //头结点
    private DoubleNode headNode = new DoubleNode(0, "", "");

    /**
     * 添加节点到最后，按顺序排列
     */
    public void addNodeRegularOrder(DoubleNode doubleNode){
        //头结点不作修改，所以使用临时节点代替
        DoubleNode tempNode=headNode;
        while(true){
            //找到下一个指针为空的位置
            if(tempNode.nextNodeAddress==null) {
                break;
            }
            //小于已有序号时，添加到前置位置
            if(doubleNode.nodeSerialNumber < tempNode.nextNodeAddress.nodeSerialNumber){
                doubleNode.nextNodeAddress=tempNode.nextNodeAddress;
                tempNode.nextNodeAddress.preNodeAddress=doubleNode;
                break;
            }
            //等同已有序号时，不添加
            if (doubleNode.nodeSerialNumber == tempNode.nextNodeAddress.nodeSerialNumber) {
                System.out.println("node number{"+doubleNode.nodeSerialNumber+"} had exist");
                return;
            }
            tempNode=tempNode.nextNodeAddress;
        }
        //替代原来的地址
        tempNode.nextNodeAddress=doubleNode;
        //向前赋值
        doubleNode.preNodeAddress=tempNode;
    }

    /**
     * 添加节点到最后
     */
    public void addNode(DoubleNode doubleNode){

        DoubleNode tempNode=this.headNode;
        while(true){
            //找到下一个指针为空的位置
            if(tempNode.nextNodeAddress==null) {
                break;
            }
            tempNode=tempNode.nextNodeAddress;
        }
        //替代原来的地址
        tempNode.nextNodeAddress=doubleNode;
        //向前赋值
        doubleNode.preNodeAddress=tempNode;
    }

    /**
     * 修改节点，根据序号对应
     */
    public void updateNode(DoubleNode node){

        DoubleNode tempNode=headNode.nextNodeAddress;
        if(tempNode == null){
            System.out.println("no data in list");
            return;
        }
        while(true){
            if(null==tempNode) {
                System.out.println("no node number["+tempNode.nodeSerialNumber+"] in list");
                break;
            }
            if(node.nodeSerialNumber==tempNode.nodeSerialNumber) {
                tempNode.nodeName =node.nodeName;
                tempNode.nickName=node.nickName;
                System.out.println("node number["+tempNode.nodeSerialNumber+"] had updated");
                break;
            }
            tempNode=tempNode.nextNodeAddress;
        }
    }

    /**
     * 删除节点，根据序号对应
     */
    public void deleteNode(int nodeSerialNumber){

        DoubleNode tempNode=headNode.nextNodeAddress;
        while(true){
            if(null==tempNode) {
                System.out.println("no node number["+tempNode.nodeSerialNumber+"] in list");
                return;
            }
            if(nodeSerialNumber==tempNode.nodeSerialNumber) {
                break;
            }
            tempNode=tempNode.nextNodeAddress;
        }
        System.out.println("node number["+nodeSerialNumber+"] had deleted");
        //使用自我删除
        tempNode.preNodeAddress.nextNodeAddress=tempNode.nextNodeAddress;
        tempNode.nextNodeAddress.preNodeAddress=tempNode.preNodeAddress;
    }

    /**
     * 显示所有节点
     */
    public void showAllNode(){

        DoubleNode tempNode=headNode.nextNodeAddress;
        if(tempNode== null){
            System.out.println("no data in list");
            return;
        }
        while(true){
            if(null==tempNode) {
                break;
            }
            System.out.println("node="+tempNode);
            tempNode=tempNode.nextNodeAddress;
        }
    }

    /**
     * 倒序显示所有节点
     */
    public void showAllNodeByReverse(){
        /* 方式一 */
        DoubleNode tempNode=headNode.nextNodeAddress;
        if(tempNode== null){
            System.out.println("no data in list");
            return;
        }
        Stack<DoubleNode> nodeStack = new Stack<DoubleNode>();
        while (null != tempNode) {
            nodeStack.push(tempNode);
            tempNode = tempNode.nextNodeAddress;
        }
        while(nodeStack.size()>0){
            System.out.println(nodeStack.pop());
        }
        /* 方式二，赋值原头结点，然后对新头节点进行changeAllNodeByReverse() */
    }

    /**
     * 获取节点的个数
     */
    public int countNode(){

        DoubleNode tempNode=headNode.nextNodeAddress;
        int count=0;
        if(tempNode== null){
            System.out.println("count node = zero ");
            return count;
        }
        while(null!=tempNode){
            count++;
            tempNode=tempNode.nextNodeAddress;
        }
        System.out.println("count node ="+count);
        return count;
    }

    /**
     * 获取倒数第X个节点
     */
    public DoubleNode getNodeSerialNumberByReverse(int target){

        DoubleNode tempNode=headNode.nextNodeAddress;
        if(tempNode== null){
            System.out.println("no data in list");
            return null;
        }
        //获取节点的个数
        int count = this.countNode();
        //获取目标节点在正数的位置
        int targetByDirect = count-target;
        while (null != tempNode) {
            //无法用序号来比对。所以使用倒计数
            if (targetByDirect == 0) {
                return tempNode;
            }
            targetByDirect--;
            tempNode = tempNode.nextNodeAddress;
        }
        System.out.println("no node number["+target+"] in list");
        return null;
    }

    /**
     * 反转所有节点,头结点不变
     */
    public void changeAllNodeByReverse() {

        if(headNode.nextNodeAddress== null){
            System.out.println("no data in list");
            return;
        }
        //创建新节点
        DoubleNode newNode = new DoubleNode(0, "", "");
        DoubleNode tempNode=headNode.nextNodeAddress;
        DoubleNode nextNode=null;
        while (null!=tempNode){
            //保存下一轮正常次序的节点
            nextNode=tempNode.nextNodeAddress;
            //将tempNode排前的子节点赋值给newNode的子节点，然后下一轮返回赋值到tempNode的子节点
            tempNode.nextNodeAddress=newNode.nextNodeAddress;
            //新节点设置上一节点地址
            if(null!=newNode.nextNodeAddress) {
                newNode.nextNodeAddress.preNodeAddress = tempNode;
            }
            //首尾相接
            newNode.nextNodeAddress=tempNode;
            //赋值下一轮正常次序的节点
            tempNode=nextNode;
        }
        headNode.nextNodeAddress=newNode.nextNodeAddress;
        this.showAllNode();
    }


    public static void main(String[] args) {

        DoubleLinkedListClass doubleLinkedListClass = new DoubleLinkedListClass();
        DoubleNode node1 = new DoubleNode(1, "宋江", "及时雨");
        DoubleNode node2 = new DoubleNode(2, "卢俊义", "玉麒麟");
        DoubleNode node3 = new DoubleNode(3, "吴用", "智多星");
        DoubleNode node4 = new DoubleNode(4, "林冲", "豹子头");
        DoubleNode node5 = new DoubleNode(4, "林冲11", "豹子头11");
        DoubleNode node6 = new DoubleNode(3, "武松", "行者");
        DoubleNode node7 = new DoubleNode(5, "柴进", "小旋风");
        DoubleNode node8 = new DoubleNode(9, "花荣", "小李广");

        doubleLinkedListClass.addNodeRegularOrder(node2);
        doubleLinkedListClass.addNodeRegularOrder(node1);
        doubleLinkedListClass.addNodeRegularOrder(node4);
        doubleLinkedListClass.addNodeRegularOrder(node3);
        doubleLinkedListClass.addNodeRegularOrder(node5);
        doubleLinkedListClass.countNode();
        doubleLinkedListClass.showAllNode();

        System.out.println("***********  test 1 **********");
        doubleLinkedListClass.updateNode(node5);
        doubleLinkedListClass.showAllNode();
        System.out.println(doubleLinkedListClass.headNode.nextNodeAddress.nextNodeAddress);
        System.out.println(doubleLinkedListClass.headNode.nextNodeAddress.nextNodeAddress.preNodeAddress);

        System.out.println("***********  test 2 **********");
        doubleLinkedListClass.deleteNode(node6.nodeSerialNumber);
        doubleLinkedListClass.showAllNode();

        System.out.println("**********  test 3 ***********");
        doubleLinkedListClass.addNodeRegularOrder(node7);
        doubleLinkedListClass.addNodeRegularOrder(node8);
        doubleLinkedListClass.showAllNode();
        System.out.println("__________________________________");
        System.out.println(doubleLinkedListClass.getNodeSerialNumberByReverse(4));

        System.out.println("************  test 4 *********");
        doubleLinkedListClass.showAllNodeByReverse();

        System.out.println("************  test 5 *********");
        doubleLinkedListClass.changeAllNodeByReverse();
        System.out.println(doubleLinkedListClass.headNode.nextNodeAddress.nextNodeAddress.nextNodeAddress.nextNodeAddress);
        System.out.println(doubleLinkedListClass.headNode.nextNodeAddress.nextNodeAddress.nextNodeAddress.nextNodeAddress.preNodeAddress);

    }

}

/**
 * 节点
 */
class DoubleNode{

    public  int nodeSerialNumber;
    public  String nodeName;
    public  String nickName;
    public  DoubleNode preNodeAddress; //上一个指针
    public  DoubleNode nextNodeAddress; //下一个指针

    public DoubleNode(int nodeSerialNumber,String nodeAddress,String nickName){

        this.nodeSerialNumber=nodeSerialNumber;
        this.nodeName =nodeAddress;
        this.nickName=nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + nodeSerialNumber +
                ", name='" + nodeName + '\'' +
                ", nickname='" + nickName + '\'' +
                '}';
    }

}
