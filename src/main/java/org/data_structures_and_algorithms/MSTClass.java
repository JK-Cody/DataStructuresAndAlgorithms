package org.data_structures_and_algorithms;

import java.util.*;

/**
 * 最小生成树问题
 */
public  class MSTClass {

    public static void main(String[] args) {

        System.out.println("***********  test 1 **********");
        char[] target = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int N = 100000; //顶点直接无连接时的默认值
        int[][] weights01 = new int[][]{
                {N, 5, 7, N, N, N, 2}, // A
                {5, N, N, 9, N, N, 3},// B
                {7, N, N, N, 8, N, N},
                {N, 9, N, N, N, 4, N},
                {N, N, 8, N, N, 5, 4},
                {N, N, N, 4, 5, N, 6},
                {2, 3, N, N, 4, 6, N}};
        NodeGroup nodeGroupForPrimClass = new NodeGroup(target.length, weights01);
        nodeGroupForPrimClass.show();
        PrimClass primClass = new PrimClass();
        primClass.createPrimGraph(nodeGroupForPrimClass, 1);

        System.out.println("***********  test 2 **********");
        int[][] weights02 = new int[][]{
                {0, 12, N, N, N, 16, 14},
                {12, 0, 10, N, N, 7, N},
                {N, 10, 0, 3, 5, 6, N},
                {N, N, 3, 0, 4, N, N},
                {N, N, 5, 4, 0, 2, 8},
                {16, 7, N, N, 2, 0, 9},
                {14, N, N, N, 8, 9, N}
        };
        NodeGroup newNodeGroupForKruskalClass = new NodeGroup(target.length, weights02);
        newNodeGroupForKruskalClass.show();
        KruskalClass kruskalClass = new KruskalClass();
        kruskalClass.createKruskalGraph(newNodeGroupForKruskalClass);

        System.out.println("***********  test 3 **********");
        int[][] weights03 = new int[][]{
                {N, 5, 7, N, N, N, 2},
                {5, N, N, 9, N, N, 3},
                {7, N, N, N, 8, N, N},
                {N, 9, N, N, N, 4, N},
                {N, N, 8, N, N, 5, 4},
                {N, N, N, 4, 5, N, 6},
                {2, 3, N, N, 4, 6, N}
        };
        NodeGroup newNodeGroupForDijkstraClass = new NodeGroup(target.length, weights03);
        newNodeGroupForDijkstraClass.show();
        char targetNode01='C';
        int node01 = (int) targetNode01 -65;
        DijkstraClass dijkstraClass = new DijkstraClass();
        dijkstraClass.createDijkstraGraph(node01,newNodeGroupForDijkstraClass);
        System.out.println();

        System.out.println("***********  test 4 **********");
        int[][] weights04 = new int[][]{
                {N, 5, 7, N, N, N, 2},
                {5, N, N, 9, N, N, 3},
                {7, N, N, N, 8, N, N},
                {N, 9, N, N, N, 4, N},
                {N, N, 8, N, N, 5, 4},
                {N, N, N, 4, 5, N, 6},
                {2, 3, N, N, 4, 6, N}
        };
        NodeGroup newNodeGroupForFloydClass = new NodeGroup(7, weights04);
        newNodeGroupForFloydClass.show();
        char targetNode02='C';
        int node02 = (int) targetNode02 -65;
        FloydClass floydClass = new FloydClass();
        floydClass.createFloydGraph(node02,newNodeGroupForDijkstraClass);
        System.out.println();
    }

    /**
     * 普利姆算法
     */
    static class PrimClass {

        /**
         * 计算每一轮连接顶点的最优权值（无向）
         * 所有的顶点须满足每个顶点起码有一条路径能连接到另外的一个顶点
         */
        public void createPrimGraph(NodeGroup nodeGroup, int beginVerte) {

            int total = nodeGroup.total;
            //顶点满足矩阵，即同一个二维数组和一维数组的索引数字都指向同一顶点，即0-6代表A-G
            int[][] weights = nodeGroup.weights;
            char[] nodeNos = nodeGroup.nodeNo;
            int minTotalWeight = 0;
            int minWeight;
            int nextIndex = 0;

            // 记录已经选择过的顶点
            boolean[] checked = new boolean[total];
            //标记起始点已选择
            checked[beginVerte] = true;
            //保存起始的选择连接方式
            int[][] selectNodeGroup = new int[total][];
            int[] selectNode = weights[beginVerte];
            selectNodeGroup[beginVerte] = selectNode;
            int index = beginVerte;
            while (total > 1) {
                minWeight = 100000;  //顶点直接无连接时的默认值
                for (int i = 0; i < selectNodeGroup.length; i++) {
                    //从备选中获取最小权值的连接选择
                    int[] ints = selectNodeGroup[i];
                    if (ints != null) {
                        for (int j = 0; j < ints.length; j++) {
                            int anInt = ints[j];
                            //属于未标记且有连接的选择
                            if (checked[j] || anInt == 100000 || anInt == -1) {
                                continue;
                            }
                            //比较当前最小
                            if (minWeight > anInt) {
                                minWeight = anInt;
                                index = i;
                                nextIndex = j;
                            }
                        }
                    }
                }
                minTotalWeight += minWeight;
                System.out.printf("%s,%s [%s] \n", nodeNos[index], nodeNos[nextIndex], minWeight);
                //标记该顶点已选择
                checked[nextIndex] = true;
                  //从备选中剔除本次选择
                if (selectNodeGroup[index] != null) {
                    selectNodeGroup[index][nextIndex] = -1;
                }
                //保存下一轮要比较的选择
                selectNode = weights[nextIndex];
                selectNodeGroup[nextIndex] = selectNode;
                index = nextIndex;
                total--;
            }
            System.out.println("minTotalWeight=" + minTotalWeight);
        }
    }

    /**
     * 克鲁斯卡尔算法
     */
     static class KruskalClass {

        /**
         * 计算连接顶点的最小权值（无向）
         * 所有的顶点须满足每个顶点起码有一条路径能连接到另外的一个顶点，且不产生回路
         */
        public void createKruskalGraph(NodeGroup nodeGroup) {

            int totalEdgeNum = nodeGroup.totalEdgeNum;
            int mimEdgeWeight=0;
            int index=0;

            //获取所有连接选择的排序结果
            nodeEdgeDataClass[] sortNodeGroup = sortNodeGroup(nodeGroup);
            int [] selectNode =new int[totalEdgeNum];
            nodeEdgeDataClass [] selectNodeEdgeData =new nodeEdgeDataClass[totalEdgeNum];
            //按照排序结果获取最小权值的选择，但非回路
            for (nodeEdgeDataClass nodeEdgeDatum : sortNodeGroup) {
                //从已选择的连接方式中判断当前两个顶点的相连是否形成回路
                int start = (int) nodeEdgeDatum.start - 65;
                int end = (int) nodeEdgeDatum.end - 65;
                //获取两个顶点的终点
                start = getEndEdge(selectNode, start);
                end = getEndEdge(selectNode, end);
                //没有回路时m
                if (start != end) {
                    //记录两个顶点的终点
                    selectNode[start] = end;
                    //记录连接方式
                    selectNodeEdgeData[index++] = nodeEdgeDatum;
                    mimEdgeWeight += nodeEdgeDatum.weight;
                }
            }
            Object[] objects = Arrays.stream(selectNodeEdgeData).filter(v -> v != null).toArray();
            System.out.println("边的选择结果:"+Arrays.toString(objects));
            System.out.println("边的总权值结果:"+mimEdgeWeight);
        }

        /**
         * 对所有连接选择进行排序
         */
        private nodeEdgeDataClass[] sortNodeGroup(NodeGroup nodeGroup) {

            int[][] weights = nodeGroup.weights;
            nodeEdgeDataClass[] nodeEdgeData = new nodeEdgeDataClass[nodeGroup.totalEdgeNum];
            char[] nodeNo = nodeGroup.nodeNo;
            int count = 0;
            //数组转换为边结构
            for (int i = 0; i < weights.length; i++) {
                for (int j = i + 1; j < weights[i].length; j++) {
                    if (weights[i][j] != 100000) {
                        nodeEdgeData[count++] = new nodeEdgeDataClass(nodeNo[i], nodeNo[j], weights[i][j]);
                    }
                }
            }
            Arrays.sort(nodeEdgeData, Comparator.comparingInt(o -> o.weight));
            System.out.println("边的排序结果："+Arrays.toString(nodeEdgeData));
            return nodeEdgeData;
        }

        /**
         * 获取最终的顶点对所有连接选择进行排序
         */
        private int getEndEdge(int[] selectNode, int node) {

            int temp = node;
            //抵达最终该点直接连接或间接连接的终点
            while (selectNode[temp] != 0) {
                temp = selectNode[temp];
            }
            return temp;
        }
    }

    /**
     * 迪杰斯特拉算法
     */
    static class DijkstraClass {

        private boolean[] checked;
        private int[] selectNodeGroup;
        private  int[] nodeEdgeWeight;

        /**
         * 计算目标顶点到其他所有顶点的最短路径（有向、广度优先）
         * 每次的连接方式是独立的，只管两点连接的最短距离，无论该点与目标顶点是直连还是间接连
         */
        public void createDijkstraGraph(int targetNode, NodeGroup nodeGroup) {
            //初始化数组
            int total = nodeGroup.total;
            this.checked = new boolean[total];
            this.nodeEdgeWeight = new int[total];
            this.selectNodeGroup = new int[total];
            // 初始化所有连接方式为不可连通的N值
            Arrays.fill(selectNodeGroup, 100000);
            Arrays.fill(nodeEdgeWeight, 100000);
            //记录起始点已连接
            checked[targetNode]=true;
            total--;
            nodeEdgeWeight[targetNode] = 0;
            //获取与初始点直连的顶点的最小权值的连接方式
            getConnectNodeMimWeight(targetNode,nodeGroup);
            //获取与初始点间接连的顶点的最小权值的连接方式
            while (total>0) {
                int index = 0;
                int minWeight = 100000;
                //有连接方式的目标顶点都记录已连接
                for (int i = 0; i < checked.length; i++) {
                    if (!checked[i] && nodeEdgeWeight[i] < minWeight) {
                        minWeight=nodeEdgeWeight[i];
                        index = i;
                    }
                }
                checked[index]=true;
                //获取目标顶点直连的最小权值的连接方式
                getConnectNodeMimWeight(index,nodeGroup);
                total--;
            }
            System.out.println("**************************");
            System.out.println(Arrays.toString(checked));
            System.out.println(Arrays.toString(selectNodeGroup));
            System.out.println(Arrays.toString(nodeEdgeWeight));
            System.out.println("从 " + (char) (targetNode + 65) + " 的最短距离为：");
            // 为了结果好认一点，格式化最后的结果
            for (int i = 0; i < nodeEdgeWeight.length; i++) {
                System.out.printf("%S(%d) ", nodeGroup.nodeNo[i], nodeEdgeWeight[i]);
            }
        }

        /**
         * 获取目标顶点直连的最小权值的连接方式
         */
        private void getConnectNodeMimWeight(int target,NodeGroup nodeGroup) {

            int[][] weights = nodeGroup.weights;
            int minWeight;
            //获取直连的连接的最小权值
            for (int i = 0; i < weights[target].length; i++) {
                if (weights[target][i] == 100000) {
                    continue;
                }
                //保存目标顶点和直连的顶点的总权值
                minWeight = nodeEdgeWeight[target] + weights[target][i];
                //比较出总权值中最小的一个,可能出现直接连接比间接连接权值更大的情况
                if (!checked[i] && minWeight < nodeEdgeWeight[i]) {
                    nodeEdgeWeight[i] = minWeight;
                    //保存连接方式，表示起始点经过 target点到达 i点
                    selectNodeGroup[i] = target;
                }
            }
        }
    }

    /**
     * 弗洛伊德算法
     */
    static class FloydClass {

        private boolean[] checked;
        private int[] selectNodeGroup;
        private  int[] nodeEdgeWeight;

        /**
         * 计算所有顶点到其他顶点的最短路径
         * 通过直连和间接连的距离总和获取最短权值的连接方式
         */
        public void createFloydGraph(int targetNode, NodeGroup nodeGroup) {

            int total = nodeGroup.total;
            char[] nodeNo = nodeGroup.nodeNo;
            int[][] weights = nodeGroup.weights;
            int[][] indirectNodeWeight = new int[total][total];
            // 将间接的连接方式的权值设置为跟顶点符合一致
//            for (int i = 0; i < indirectNodeWeight.length; i++) {
//                //如A的所有数组元素均为A
//                Arrays.fill(indirectNodeWeight[i], i);
//            }
            //全循环的方式获取每个顶点的最小权值连接方式
            for(int i=0;i<total;i++){  //获取初始点
                for(int j=0;j<total;j++){  //获取间接点
                    for(int k=0;k<total;k++){  //获取终点
                        // 间接连时获取j、i、k的权值总和
                        int weight_ji = weights[j][i];
                        int weight_ik = weights[i][k];
                        int totalWeight = weight_ji + weight_ik;
                        //与当前j顶点直连k顶点的权值比较大小
                        int weight_jk = weights[j][k];
                        if (totalWeight < weight_jk) {
                            weights[j][k] = totalWeight;
                            //如果间接的权值更小，保存i顶点到k顶点的权值
                            indirectNodeWeight[j][k] = indirectNodeWeight[i][k];
                        }
                    }
                }
            }
            System.out.println("***********************");
            for (int i = 0; i < weights.length; i++) {
                System.out.println(nodeNo[i] + " 到其他顶点的最短距离");
                for (int k = 0; k < weights.length; k++) {
                    System.out.printf("%-10s", " → "+ nodeNo[k] + " = " + weights[i][k] + "");
                }
                System.out.println();
            }
        }
    }
}

/**
 * 树节点集合
 */
class NodeGroup{

    int total;  // 顶点个数
    int totalEdgeNum; //多少条边
    int[][] weights;  // 记录相邻顶点的权值
    char[] nodeNo; //顶点的标记号码

    public NodeGroup(int total, int[][] weights) {
        this.total = total;
        this.weights = weights;
        this.nodeNo = new char[total];
        //将顶点标记号码,从A到G
        for (int i = 0; i < total; i++) {
            nodeNo[i] = (char) (65 + i);
        }
        for (int i = 0; i < weights.length; i++) {
            for (int j = i + 1; j < weights.length; j++) {
                if (weights[i][j] != 100000) {
                    totalEdgeNum++;
                }
            }
        }
    }

    /**
     * 显示顶点的连接矩阵（无向）
     */
    public void show() {
        System.out.printf("%-8s"," ");
        //输出顶点内容
        for (char vertex : nodeNo) {
            //少于8位的顶点，右侧用空格补位
            System.out.printf("%-8s", vertex + " ");
        }
        System.out.println();
        //输出顶点权值
        for (int i = 0; i < weights.length; i++) {
            System.out.printf("%-8s", nodeNo[i] + " ");
            for (int j = 0; j < weights.length; j++) {
                System.out.printf("%-8s", weights[i][j] + " ");
            }
            System.out.println();
        }
        System.out.printf("共有 %d 条边", totalEdgeNum);
        System.out.println();
    }
}

/**
 * 树节点的边(有向)
 */
class  nodeEdgeDataClass {

    char start; //首连接点
    char end; //尾连接点
    int weight; //权值

    public nodeEdgeDataClass(char start, char end, int weight) {

        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {

        return start + "," + end + " [" + weight + "]";
    }
}