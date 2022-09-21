package org.data_structures_and_algorithms.grap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 图算法
 */
public class Grap {
    //存放图的所有的顶点
    private final List<String> vertexs;
    //存放边的关系（顶点之间的关系）
    private final int[][] edges;
    //存放有多少条边
    private int numOfEdges = 0;

    /*
     * 存放有几个顶点
     */
    public Grap(int n) {

        vertexs = new ArrayList<>(n);
        edges = new int[n][n];
    }

    /**
     * 添加顶点
     */
    public void insertVertex(String vertex) {

        vertexs.add(vertex);
    }

    /**
     * 添加边的关系
     */
    public void insertEdge(int v1, int v2, int weight) {  //权值
        //两个顶点互连
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    /**
     * 获取边的数量
     */
    public int getNumOfEdges() {

        return numOfEdges;
    }

    /**
     * 根据下标获得顶点的值
     */
    public String getValueByIndex(int i) {

        return vertexs.get(i);
    }

    /**
     * 显示
     */
    public void show() {

        System.out.print("  ");
        for (String vertex : vertexs) {
            System.out.print(vertex + " ");
        }
        System.out.println();
        for (int i = 0; i < edges.length; i++) {
            System.out.print(vertexs.get(i) + " ");
            for (int j = 0; j < edges.length; j++) {
                System.out.print(edges[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 深度优先遍历一个节点
     */
    public void DFSSimgleVerte(int index,boolean[] checked) {

        System.out.println(vertexs.get(index) + " -> ");
        //记录当前顶点被检查
        checked[index] = true;
        int nextIndex=-1;
        //获取当前顶点连接的第一个顶点，A-B
        for (int j = index; j < vertexs.size(); j++) {
            if (edges[index][j] == 1) {
                System.out.println("获取第一个顶点:"+index+"-"+j);
                nextIndex=j;
                break;
            }
        }
        //有连接顶点时
        while (nextIndex != -1) {
            // 连接顶点已经访问过时
            if (checked[nextIndex]) {
                //顺延获取当前节点的下一个连接顶点,A-D
                int j = nextIndex + 1;
                nextIndex=-1;
                for (; j < vertexs.size(); j++) {
                    if (edges[index][j] == 1) {
                        System.out.println("顺延获取下一个连接顶点:"+index+"-"+j);
                        nextIndex=j;
                        break;
                    }
                }
            // 连接顶点未访问过时
            } else {
                //递归获取第一个连接的顶点的第一个连接顶点,B-C
                DFSSimgleVerte(nextIndex,checked);
            }
        }
    }

    /**
     * 所有顶点深度优先遍历
     */
    public void DFSOrder() {
        //记录每个顶点是否被检查连接的顶点，如检查A-C相连后，就不需要再检查C-A相连
        boolean[] checked = new boolean[vertexs.size()];
        for (int i = 0; i < vertexs.size(); i++) {
            //不是已检查的顶点时
            if (!checked[i]) {
                //深度优先遍历一个节点
                DFSSimgleVerte(i, checked);
            }
        }
    }

    /**
     * 广度优先遍历一个节点
     */
    public void BSFSimgleVerte(int index,boolean[] checked) {
        //记录当前顶点被检查
        checked[index] = true;
        //记录每个顶点的列表
        LinkedList<Integer> connectedVerteList = new LinkedList<>();
        int nextIndex=-1;
        int listNode;

        System.out.print(vertexs.get(index) + " -> ");
        //添加当前顶点到列表最后
        connectedVerteList.addLast(index);
        /* 因为第一个顶点会持续查找连接的所有顶点,所以所有顶点都被记录被检查，不会再进入当前方法里，
        因此在第一个顶点执行期间，就将已检查的连接顶点放入队列，再取出来查找它后面的连接顶点 */
        while (!connectedVerteList.isEmpty()) {
            //获取并删除列表的首位顶点
            listNode = connectedVerteList.removeFirst();
            // 查找首位顶点连接的第一个顶点
            for (int j = listNode; j < vertexs.size(); j++) {
                if (edges[listNode][j] == 1) {
                    System.out.println("获取第一个顶点:"+listNode+"-"+j);
                    nextIndex=j;
                    break;
                }
            }
            //持续查找首位顶点连接的所有顶点
            while (nextIndex!=-1) {
                //连接顶点没有被访问过
                if (!checked[nextIndex]) {
                    System.out.print(vertexs.get(nextIndex) + " -> ");
                    //记录被访问过
                    checked[nextIndex] = true;
                    //添加连接顶点到列表最后
                    connectedVerteList.addLast(nextIndex);
                }
                //查找下一个连接顶点
                int j = nextIndex + 1;
                nextIndex=-1;
                for (; j < vertexs.size(); j++) {
                    if (edges[listNode][j] == 1) {
                        System.out.println("获取下一个连接顶点:"+listNode+"-"+j);
                        nextIndex=j;
                        break;
                    }
                }
            }
        }
    }

    /**
     * 所有顶点广度优先遍历
     */
    public void BSFOrder() {
        //记录每个顶点是否被检查连接的顶点，如检查A-C相连后，就不需要再检查C-A相连
        boolean[] checked = new boolean[vertexs.size()];
        for (int i = 0; i < vertexs.size(); i++) {
            //不是已检查的顶点时
            if (!checked[i]) {
                //广度优先遍历一个节点
                BSFSimgleVerte(i, checked);
            }
        }
    }

    public static void main(String[] args) {

        int n = 5;
        String[] vertexValue = {"A", "B", "C", "D", "E"};
        Grap grap = new Grap(n);
        for (String value : vertexValue) {
            grap.insertVertex(value);
        }
        grap.insertEdge(0, 1, 1);
        grap.insertEdge(0, 2, 1);
        grap.insertEdge(1, 2, 1);
        grap.insertEdge(1, 3, 1);
        grap.insertEdge(1, 4, 1);
        grap.show();
        System.out.println("*******深度优先*********");
        grap.DFSOrder();

        System.out.println("********广度优先********");
        grap.BSFOrder();
    }
}
