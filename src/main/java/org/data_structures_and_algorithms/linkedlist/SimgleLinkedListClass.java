package org.data_structures_and_algorithms.linkedlist;

import java.util.Stack;

/**
 * 单链表
 */
public class SimgleLinkedListClass {
    //头结点
    private Node headNode = new Node(0, "", "");

    /**
     * 添加节点，按顺序排列
     */
    public void addNodeRegularOrder(Node node){
        //头结点不作修改，所以使用临时节点代替
        Node tempNode=this.headNode;
        while(true){
            //找到下一个指针为空的位置
            if(tempNode.nextNodeAddress==null) {
                break;
            }
            //小于已有序号时，添加到前置位置
            if(node.nodeSerialNumber < tempNode.nextNodeAddress.nodeSerialNumber){
                node.nextNodeAddress=tempNode.nextNodeAddress;
                break;
            }
            //等同已有序号时，不添加
            if (node.nodeSerialNumber == tempNode.nextNodeAddress.nodeSerialNumber) {
                System.out.println("node number{"+node.nodeSerialNumber+"} had exist");
                return;
            }
            tempNode=tempNode.nextNodeAddress;
        }
        //替代原来的赋值地址
        tempNode.nextNodeAddress=node;
    }

    /**
     * 添加节点到最后
     */
    public void addNode(Node node){

        Node tempNode=this.headNode;
        while(true){
            //找到下一个指针为空的位置
            if(tempNode.nextNodeAddress==null) {
                break;
            }
            tempNode=tempNode.nextNodeAddress;
        }
        //赋值地址到空的位置
        tempNode.nextNodeAddress=node;
    }

    /**
     * 修改节点，根据序号对应
     */
    public void updateNode(Node node){

        Node tempNode=headNode.nextNodeAddress;
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

        Node tempNode=headNode;
        while(true){
            if(null==tempNode.nextNodeAddress) {
                System.out.println("no node number["+tempNode.nodeSerialNumber+"] in list");
                return;
            }
            if(nodeSerialNumber==tempNode.nextNodeAddress.nodeSerialNumber) {
                break;
            }
            tempNode=tempNode.nextNodeAddress;
        }
        System.out.println("node number["+nodeSerialNumber+"] had deleted");
        //将目标节点指向下一个节点
        tempNode.nextNodeAddress=tempNode.nextNodeAddress.nextNodeAddress;
    }

    /**
     * 显示所有节点
     */
    public void showAllNode(){

        Node tempNode=headNode.nextNodeAddress;
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
        Node tempNode=headNode.nextNodeAddress;
        if(tempNode== null){
            System.out.println("no data in list");
            return;
        }
        Stack<Node> nodeStack = new Stack<Node>();
        while (null != tempNode) {
            nodeStack.push(tempNode);
            tempNode = tempNode.nextNodeAddress;
        }
        while(nodeStack.size()>0){
            System.out.println(nodeStack.pop());
        }
        /* 方式二，赋值原头结点，然后对新头节点进行changeAllNodeByReverse()操作（不改变原头节点的状态） */
    }

    /**
     * 获取节点的个数
     */
    public int countNode(){

        Node tempNode=headNode.nextNodeAddress;
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
    public Node getNodeSerialNumberByReverse(int target){

        Node tempNode=headNode.nextNodeAddress;
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
        Node newNode = new Node(0, "", "");
        Node tempNode=headNode.nextNodeAddress;
        Node nextNode=null;
        //
        while (null!=tempNode){
            //保存下一轮正常次序的节点
            nextNode=tempNode.nextNodeAddress;
            //将tempNode排前的子节点赋值给newNode的子节点，然后下一轮返回赋值到tempNode的子节点
            //tempNode的可向下去值的变化为{1，2,3},{1,null} / {2,3},{2,1,null} / {3,null},{3,2,1,null}
            tempNode.nextNodeAddress=newNode.nextNodeAddress;
            //newNode的可向下去值的变化为{0,null} >> {0,1,null} >> {0,2,1,null} >> {0,3,2,1,null}
            newNode.nextNodeAddress=tempNode;
            //赋值下一轮正常次序的节点
            tempNode=nextNode;
        }
        headNode.nextNodeAddress=newNode.nextNodeAddress;
        this.showAllNode();
    }

    /**
     * 合并两个链表,按顺序排列，须均有值
     * 两个链表的内容均改变
     */
    public Node combineDoubleNodeIntoOne( Node one,Node two) {

        Node oneTemp=one;
        if(oneTemp.nextNodeAddress== null){
            System.out.println("no data in list tempNode");
            return two;
        }
        Node twoTemp=two.nextNodeAddress;
        if(twoTemp== null){
            System.out.println("no data in list newTempNode");
            return one;
        }
        Node indexNode=null;
        //保存oneTemp最初的地址
        Node index=oneTemp;
        while(true){
            if(oneTemp.nextNodeAddress==null){
                oneTemp.nextNodeAddress=twoTemp;
                break;
            }
            //冒泡排序，调整oneTemp的所有子节点的次序
            if(oneTemp.nextNodeAddress.nodeSerialNumber>=twoTemp.nodeSerialNumber) {
                //two的节点作为one的下一节点的地址
                indexNode=oneTemp.nextNodeAddress;
                oneTemp.nextNodeAddress=twoTemp;
                //赋值one的下一节点的地址,作继续比较
                twoTemp=indexNode;
            }
            oneTemp = oneTemp.nextNodeAddress;
        }
        return index;
    }


    public static void main(String[] args) {

        SimgleLinkedListClass simgleLinkedListClass = new SimgleLinkedListClass();
        Node node1 = new Node(1, "宋江", "及时雨");
        Node node2 = new Node(2, "卢俊义", "玉麒麟");
        Node node3 = new Node(3, "吴用", "智多星");
        Node node4 = new Node(4, "林冲", "豹子头");
        Node node5 = new Node(4, "林冲11", "豹子头11");
        Node node6 = new Node(3, "武松", "行者");
        Node node7 = new Node(5, "柴进", "小旋风");
        Node node8 = new Node(9, "花荣", "小李广");

        simgleLinkedListClass.addNodeRegularOrder(node2);
        simgleLinkedListClass.addNodeRegularOrder(node1);
        simgleLinkedListClass.addNodeRegularOrder(node4);
        simgleLinkedListClass.addNodeRegularOrder(node3);
        simgleLinkedListClass.addNodeRegularOrder(node5);
        simgleLinkedListClass.countNode();

        simgleLinkedListClass.showAllNode();
        System.out.println("***********  test 1 **********");

        simgleLinkedListClass.updateNode(node5);
        simgleLinkedListClass.showAllNode();

        System.out.println("***********  test 2 **********");
        simgleLinkedListClass.deleteNode(node3.nodeSerialNumber);
        simgleLinkedListClass.showAllNode();
        simgleLinkedListClass.countNode();

        System.out.println("**********  test 3 ***********");
        simgleLinkedListClass.addNodeRegularOrder(node7);
        simgleLinkedListClass.addNodeRegularOrder(node8);
        simgleLinkedListClass.showAllNode();
        System.out.println(simgleLinkedListClass.getNodeSerialNumberByReverse(4));

        System.out.println("************  test 4 *********");
        simgleLinkedListClass.showAllNodeByReverse();

        System.out.println("************  test 5 *********");
        simgleLinkedListClass.changeAllNodeByReverse();

        System.out.println("************  test 6 *********");

        SimgleLinkedListClass one = new SimgleLinkedListClass();
        Node newNode1 = new Node(1, "1宋江", "及时雨");
        Node newNode2 = new Node(3, "1卢俊义", "玉麒麟");
        Node newNode3 = new Node(4, "1吴用", "智多星");
        Node newNode4 = new Node(5, "1林冲", "豹子头");
        Node newNode5 = new Node(9, "1武松", "行者");
        one.addNodeRegularOrder(newNode1);
        one.addNodeRegularOrder(newNode2);
        one.addNodeRegularOrder(newNode3);
        one.addNodeRegularOrder(newNode4);
        one.addNodeRegularOrder(newNode5);

        SimgleLinkedListClass two = new SimgleLinkedListClass();
        Node newNode6 = new Node(2, "2柴进", "小旋风");
        Node newNode7 = new Node(3, "2宋江", "及时雨");
        Node newNode8 = new Node(4, "2卢俊义", "玉麒麟");
        Node newNode9 = new Node(6, "2吴用", "智多星");
        Node newNode10 = new Node(7, "2林冲", "豹子头");
        Node newNode11 = new Node(8, "2武松", "行者");
        Node newNode12 = new Node(10, "2花荣", "小李广");
        two.addNodeRegularOrder(newNode6);
        two.addNodeRegularOrder(newNode7);
        two.addNodeRegularOrder(newNode8);
        two.addNodeRegularOrder(newNode9);
        two.addNodeRegularOrder(newNode10);
        two.addNodeRegularOrder(newNode11);
        two.addNodeRegularOrder(newNode12);

        Node combine = simgleLinkedListClass.combineDoubleNodeIntoOne(one.headNode, two.headNode);
        while (combine != null) {
            System.out.println(combine);
            combine = combine.nextNodeAddress;
        }
    }

}

/**
 * 节点
 */
class Node{

    public  int nodeSerialNumber;
    public  String nodeName;
    public  String nickName;
    public  Node nextNodeAddress; //下一个指针

    public Node(int nodeSerialNumber,String nodeName,String nickName){

        this.nodeSerialNumber=nodeSerialNumber;
        this.nodeName =nodeName;
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