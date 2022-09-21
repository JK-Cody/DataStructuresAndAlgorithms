package org.data_structures_and_algorithms.hashtable;

/**
 * 哈希表
 */
public class HashTableClass {
    // 链表数组
    private nodeLinkedList[] linkedArray;
    private final int size;

    /**
     * 创建哈希表
     */
    public HashTableClass(int size) {
        this.size = size;
        this.linkedArray = new nodeLinkedList[size];
        // 初始化
        for (int i = 0; i < size; i++) {
            linkedArray[i] = new nodeLinkedList();
        }
    }

    /**
     * 散列函数,将元素id设定为一个多位数
     */
    private int hashFun(int id) {

        return id % size;
    }

    /**
     * 添加链表元素
     */
    public void add(Node node) {
        // 首先需要确定：该员工的 id 所在的哈希位置，用散列函数来计算
        int id = node.id;
        int index = hashFun(id);
        linkedArray[index].add(node);
    }

    /**
     * 打印链表
     */
    public void list() {
        for (int i = 0; i < size; i++) {
            linkedArray[i].list(i);
        }
    }

    /**
     * 根据id查找链表元素
     */
    public Node findById(int id) {

        int no = hashFun(id);
        if (no > size || no < 0) {
            System.out.printf("id = %d 异常，计算出目标链表为 %d \n", id, no);
            return null;
        }
        Node node = linkedArray[no].findById(id);
        if (node == null) {
            System.out.printf("在第 %d 条链表中未找到 id = %d 的雇员 \n", no, id);
        } else {
            System.out.printf("在第 %d 条链表中找到 id = %d 的雇员, name = %s \n", no, id, node.name);
        }
        return node;
    }

    /**
     * 删除链表元素
     */
    public Node deleteById(int id) {
        // 先找到该 id 属于那一条链表
        int no = hashFun(id);
        // 先判断边界
        if (no > size || no < 0) {
            System.out.printf("id = %d 异常，计算出目标链表为 %d \n", id, no);
            return null;
        }

        Node node = linkedArray[no].deleteById(id);
        if (node == null) {
            System.out.printf("在第 %d 条链表中未找到 id = %d 的雇员，删除失败 \n", no, id);
        } else {
            System.out.printf("在第 %d 条链表中找到 id = %d 的雇员, name = %s ,删除成功\n", no, id, node.name);
        }
        return node;
    }


    public static void main(String[] args) {

        HashTableClass hashtable = new HashTableClass(7);
        hashtable.add(new Node(1, "小明"));
        hashtable.add(new Node(2, "小红"));
        hashtable.add(new Node(3, "小蓝"));
        System.out.println();
        hashtable.list();
        hashtable.add(new Node(3, "小蓝"));
        hashtable.add(new Node(4, "小蓝4"));
        hashtable.add(new Node(5, "小蓝5"));
        System.out.println();
        hashtable.list();
        hashtable.deleteById(1);
        hashtable.findById(2);
    }
}

/**
 * 链表
 */
class nodeLinkedList {

    private Node firstNode;

    /**
     * 添加元素
     */
    public void add(Node node) {
        if (firstNode == null) {
            firstNode = node;
            return;
        }
        Node temp = firstNode;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
    }

    /**
     * 打印
     */
    public void list(int no) {
        if (firstNode == null) {
            System.out.println("链表为空");
            return;
        }
        Node node = firstNode;
        while (true) {
            System.out.printf("%d : \t id=%d,\t name=%s \n", no, node.id, node.name);
            if (node.next == null) {
                break;
            }
            node = node.next;
        }
    }

    /**
     * 根据 ID 查找元素
     */
    public Node findById(int id) {
        if (firstNode == null) {
            return null;
        }
        // 有 head 则循环查找链表
        Node temp = firstNode;
        while (true) {
            if (temp.id == id) {
                // 已经找到
                break;
            }
            if (temp.next == null) {
                // 当下一个为空的时候，则表示没有找到
                temp = null;
                break;
            }
            temp = temp.next;
        }
        return temp;
    }

    /**
     * 按 ID 删除元素
     */
    public Node deleteById(int id) {
        if (firstNode == null) {
            return null;
        }
        // 有 head 则循环查找链表
        Node temp = firstNode;
        Node prev = firstNode;
        while (true) {
            if (temp.id == id) {
                // 已经找到
                break;
            }
            if (temp.next == null) {
                // 当下一个为空的时候，则表示没有找到
                temp = null;
                break;
            }
            prev = temp;  // 标记上一个雇员
            temp = temp.next;
        }
        if (temp == null) {
            return null;
        }
        // 如果找到的就是 head ,则删除自己
        if (firstNode == temp) {
            firstNode = temp.next;
            return temp;
        }
        // 如果已经找到目标元素，从它的上一个雇员节点中删掉自己
        prev.next = temp.next;
        return temp;
    }
}

/**
 * 节点
 */
class Node {
    public int id;
    public String name;
    public Node next;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }
}