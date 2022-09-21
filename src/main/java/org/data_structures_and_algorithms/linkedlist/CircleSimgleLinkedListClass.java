package org.data_structures_and_algorithms.linkedlist;

/**
 * 单链表环形状
 */
public class CircleSimgleLinkedListClass {

    //头节点
    static Node headNode = new Node(0, "", "");
    //无头节点单链表的第一个节点
    private Node firstHead=null;

    /**
     * 初始化,形成回环
     */
    static {
        headNode.nextNodeAddress=headNode;
    }

    /**
     * 添加节点到最后
     */
    public void addNodeBycircleOrder(Node node){

        Node tempNode= headNode;
        node.nextNodeAddress=tempNode;
        while(true){
            //获取下一节点序号为0的节点
            if(tempNode.nextNodeAddress.nodeSerialNumber==0) {
                tempNode.nextNodeAddress = node;
                break;
            }
            tempNode=tempNode.nextNodeAddress;
        }
    }

    /**
     * 显示所有节点
     */
    public void showAllNode(){

        Node tempNode= headNode.nextNodeAddress;
        if(tempNode== null){
            System.out.println("no data in list");
            return;
        }
        while(true){
            System.out.println(tempNode);
            tempNode=tempNode.nextNodeAddress;
            //获取下一节点序号为0的节点
            if(tempNode.nodeSerialNumber==0) {
                break;
            }
        }
    }

    /**
     * 新对象复制当前链表
     */
    public Node copythisNode(Node copyHeadNode){

        Node tempNode= headNode.nextNodeAddress;
        if(tempNode== null){
            System.out.println("no data in list");
            return null;
        }
        Node head=copyHeadNode;
        while (true){
            if(tempNode.nodeSerialNumber==0){
                //最后连接头结点
                copyHeadNode.nextNodeAddress=head;
                break;
            }
            //初始化
            copyHeadNode.nextNodeAddress=new Node(-1,"","");
            //复制属性
            copyHeadNode.nextNodeAddress.nodeSerialNumber=tempNode.nodeSerialNumber;
            copyHeadNode.nextNodeAddress.nodeName=tempNode.nodeName;
            copyHeadNode.nextNodeAddress.nickName=tempNode.nickName;
            copyHeadNode=copyHeadNode.nextNodeAddress;
            tempNode=tempNode.nextNodeAddress;
        }
        return head;
    }

    /**
     * 剔除链表的头结点
     */
    public Node deleteHeadNode(Node headNode){

        Node tempNode= headNode.nextNodeAddress;
        Node first = headNode.nextNodeAddress;
        while (true){
           if(tempNode.nextNodeAddress.nodeSerialNumber==0){
               tempNode.nextNodeAddress=tempNode.nextNodeAddress.nextNodeAddress;
               tempNode=tempNode.nextNodeAddress;
               break;
           }
            tempNode=tempNode.nextNodeAddress;
        }
        while(true){
            tempNode=tempNode.nextNodeAddress;
            //获取下一节点序号为0的节点
            if(tempNode==first) {
                break;
            }
        }
        return tempNode;
    }

    /**
     * 获取节点的个数,不包括头结点
     */
    public int countNode(){

        Node tempNode=headNode.nextNodeAddress;
        if(tempNode== null){
            System.out.println("no data in list");
            return 0;
        }
        Node first=headNode;
        int count=0;
        while(tempNode!=first){
            count++;
            tempNode=tempNode.nextNodeAddress;
        }
        return count;
    }

    /**
     * 创建一个无头结点的单链表循环式
     */
    public void createListWithoutHeadNode(int count){

        if(count < 1){
            System.out.println("节点数量过少");
            return;
        }
        //创建环形列表
        Node tempNode = null;
        //递增的节点循环
        for (int i =1; i < count+1; i++) {
            //根据编号创建节点
            Node node = new Node(i,"","");
            //初始化
            if (i == 1) {
                firstHead = node;
                node.nextNodeAddress=firstHead;  // 构成环形
                tempNode = firstHead;
            } else {
                tempNode.nextNodeAddress=node;
                node.nextNodeAddress=firstHead;
                tempNode = node;
            }
        }
    }

    /**
     * 遍历一个无头结点的单链表循环式
     */
    public void showListWithoutHeadNode(){

        if (firstHead == null){
            System.out.println("链表为空");
            return;
        }
        Node tempNode =new Node(1,"","");
        tempNode = firstHead;
        while (true){
            System.out.print("no="+tempNode.nodeSerialNumber+"\n");
            if (tempNode.nextNodeAddress == firstHead){
                break;
            }
            tempNode = tempNode.nextNodeAddress;
        }
    }

    /**
     * 约瑟夫问题,每一轮按照指定的顺序获取节点,不包括头结点
     * 从第一个节点开始计数
     */
    public Node getNodeByCircleOrder(int circleOrder){

        if(headNode.nextNodeAddress== null){
            System.out.println("no data in list");
            return null;
        }
//复制链表，不修改原链表的内容
        Node copythisNode=new Node(0,"","");
        copythisNode = this.copythisNode(copythisNode);
//剔除复制链表的头结点
        copythisNode= this.deleteHeadNode(copythisNode);

        Node tempNode= copythisNode;
        Node newTempNode= new Node(0,"","");
        Node first=newTempNode;  //记录第一个节点
        int count=circleOrder; //每一轮的移动到目标节点的计数
        int total = this.countNode(); //获取的次数

        while(true){
            --count;
            //保存目标节点
            if(count==1){
                newTempNode.nextNodeAddress=tempNode.nextNodeAddress;
                newTempNode=newTempNode.nextNodeAddress;
                //剔除当前节点，连接下一节点
                tempNode.nextNodeAddress=tempNode.nextNodeAddress.nextNodeAddress;
                count=circleOrder;
                total--;
            }
            tempNode=tempNode.nextNodeAddress;
            //直到获取次数归零
            if(total==0) {
                //连接第一个节点
                tempNode.nextNodeAddress=first;
                break;
            }
        }
        //输出节点
        total = this.countNode();
        while (true){
            System.out.println("circle node ="+first);
            first=first.nextNodeAddress;
            if(total==0){
                break;
            }
            total--;
        }
        return copythisNode;
    }

    /**
     * 约瑟夫问题,每一轮按照指定的顺序获取节点
     * 无头结点,从自定义起始节点开始计数
     */
    public void getNodeSetStartNumberByCircleOrder(int startNo,int circleOrder){

        if (firstHead == null){
            System.out.println("链表为空");
            return;
        }
        if (startNo < 1 || startNo > circleOrder){
            System.out.println("参数输入错误");
            return;
        }
        Node tempNode=firstHead;  //获取的目标节点的前一个节点
        //指向开始之前的最后一个节点
        while(true){
            if(tempNode.nextNodeAddress==firstHead){
                break;
            }
            tempNode=tempNode.nextNodeAddress;
        }
        //均移动到自定义起始节点的前一个节点 和 自定义起始节点
        for (int i = 0; i < startNo -1 ;i++){
            firstHead = firstHead.nextNodeAddress;
            tempNode = tempNode.nextNodeAddress;
        }
        while(true){
            //当只剩下一个节点时
            if(tempNode==firstHead){
                System.out.println("last no="+firstHead);
                break;
            }
            //每一轮的移动到目标节点的计数
            for (int i = 0; i < circleOrder -1 ;i++){
                firstHead = firstHead.nextNodeAddress;
                tempNode = tempNode.nextNodeAddress;
            }
            System.out.println("no="+firstHead);
            firstHead=firstHead.nextNodeAddress;
            tempNode.nextNodeAddress=firstHead;
        }
    }

    public static void main(String[] args) {

        CircleSimgleLinkedListClass circleSimgleLinkedListClass = new CircleSimgleLinkedListClass();
        Node node1 = new Node(1, "宋江", "及时雨");
        Node node2 = new Node(2, "卢俊义", "玉麒麟");
        Node node3 = new Node(3, "吴用", "智多星");
        Node node4 = new Node(4, "林冲", "豹子头");
        Node node5 = new Node(7, "柴进", "小旋风");
        Node node6 = new Node(9, "花荣", "小李广");
        Node node7 = new Node(9, "杨志", "青面兽");

        circleSimgleLinkedListClass.addNodeBycircleOrder(node2);
        circleSimgleLinkedListClass.addNodeBycircleOrder(node1);
        circleSimgleLinkedListClass.addNodeBycircleOrder(node4);
        circleSimgleLinkedListClass.addNodeBycircleOrder(node3);
        circleSimgleLinkedListClass.addNodeBycircleOrder(node6);
        circleSimgleLinkedListClass.addNodeBycircleOrder(node5);
        circleSimgleLinkedListClass.addNodeBycircleOrder(node7);
        circleSimgleLinkedListClass.showAllNode();

        System.out.println("***********  test 1 **********");
        Node test=new Node(0,"","");
        Node node = circleSimgleLinkedListClass.copythisNode(test);
        while(true){
            System.out.println(node);
            node=node.nextNodeAddress;
            //获取下一节点序号为0的节点
            if(node.nodeSerialNumber==0) {
                break;
            }
        }

        System.out.println("***********  test 2 **********");
        circleSimgleLinkedListClass.getNodeByCircleOrder(2);

        System.out.println("***********  test 3 **********");
        circleSimgleLinkedListClass.createListWithoutHeadNode(6);
        circleSimgleLinkedListClass.showListWithoutHeadNode();
        circleSimgleLinkedListClass.getNodeSetStartNumberByCircleOrder(1,2);

    }

}
