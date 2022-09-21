package org.data_structures_and_algorithms.binarytree;

/**
 * 多叉树
 */
public class MultiwayBinaryTree {

    public static void main(String[] args) {

    }

    /**
     * 多叉树—— 2-3数
     */
    public static class TwoAndThreeTree <Key extends Comparable<Key>,Value> {

        public TwoAndThreeTreeNode root = new TwoAndThreeTreeNode();

        /**
         * 查找节点数据中指定的key的value
         */
        public Value find(Key key) {

            TwoAndThreeTreeNode curNode = root;
            int childNum;

            while (true) {
                //匹配到时
                if ((childNum = curNode.findItem(key)) != -1) {
                    return (Value) curNode.itemDatas[childNum].value;
                }
                // 直到叶子节点还没有找到时
                else if (curNode.isLeaf()) {
                    return null;
                } else {
                    //查询子节点
                    curNode = getNextChildNode(curNode,key);
                }
            }
        }

        /**
         * 查找节点数据中指定的key的节点（可能为左子结点，中间子节点，右子节点）
         */
        private TwoAndThreeTreeNode getNextChildNode(TwoAndThreeTreeNode node,Key key){

            for (int i = 0; i < node.itemNum; i++) {
                if (node.getItemData(i).key.compareTo(key)>0){
                    return node.getChildNodes(i);
                }
                else if (node.getItemData(i).key.compareTo(key) == 0){
                    return node;
                }
            }
            return node.getChildNodes(node.getItemDataNum());
        }

        /**
         * 插入节点
         */
        public void insert(Key key,Value value){

            Data data = new Data(key,value);
            TwoAndThreeTreeNode curNode = root;

            while(true){
                if (curNode.isLeaf()){
                    break;
                }else{
                    curNode = getNextChildNode(curNode,key);
                    for (int i = 0; i < curNode.getItemDataNum(); i++) {
                        // key相等时
                        if (curNode.getItemData(i).key.compareTo(key) == 0){
                            curNode.getItemData(i).value =value;
                            return;
                        }
                    }
                }
            }
            // 若当前结点已满，进行裂变
            if (curNode.isFull()){
                splitNode(curNode,data);
            }
            // 直接插入
            else {
                curNode.insertData(data);
            }
        }

        /**
         * 裂变当前结点
         */
        private void splitNode(TwoAndThreeTreeNode node, Data data) {  //裂变的结点,保存的键值对
            //父节点
            TwoAndThreeTreeNode parent = node.getParent();
            // 保存最大的键值对
            TwoAndThreeTreeNode maxNode = new TwoAndThreeTreeNode();
            // 保存中间的节点
            TwoAndThreeTreeNode middleNode = new TwoAndThreeTreeNode();
            //保存中间的数据
            Data mid;

            if (data.key.compareTo(node.getItemData(0).key)<0){
                maxNode.insertData(node.removeItemData());
                mid = node.removeItemData();
                node.insertData(data);
            }else if (data.key.compareTo(node.getItemData(1).key)<0){
                maxNode.insertData(node.removeItemData());
                mid = data;
            }else{
                mid = node.removeItemData();
                maxNode.insertData(data);
            }
            if (node == root){
                root = middleNode;
            }

            /**
             * 将newNode2和node以及newNode连接起来
             * 其中node连接到newNode2的左子树，newNode
             * 连接到newNode2的右子树
             */
            //插入中间的数据
            middleNode.insertData(mid);
            //
            middleNode.connectChildNodes(0,node);
            middleNode.connectChildNodes(1,maxNode);
            //连接父节点和中间的节点
            connectNode(parent,middleNode);
        }

        /**
         * 连接父节点和中间的节点
         */
        private void connectNode(TwoAndThreeTreeNode parent, TwoAndThreeTreeNode node) {

            Data data = node.getItemData(0);
            if (node == root){
                return;
            }
            // 假如父节点为3-结点
            if (parent.isFull()){
                // 爷爷结点（爷爷救葫芦娃）
                TwoAndThreeTreeNode gParent = parent.getParent();
                TwoAndThreeTreeNode newNode = new TwoAndThreeTreeNode();
                TwoAndThreeTreeNode temp1,temp2;
                Data itemData;

                if (data.key.compareTo(parent.getItemData(0).key)<0){
                    temp1 = parent.disconnectChildNodes(1);
                    temp2 = parent.disconnectChildNodes(2);
                    newNode.connectChildNodes(0,temp1);
                    newNode.connectChildNodes(1,temp2);
                    newNode.insertData(parent.removeItemData());

                    itemData = parent.removeItemData();
                    parent.insertData(itemData);
                    parent.connectChildNodes(0,node);
                    parent.connectChildNodes(1,newNode);
                }else if(data.key.compareTo(parent.getItemData(1).key)<0){
                    temp1 = parent.disconnectChildNodes(0);
                    temp2 = parent.disconnectChildNodes(2);
                    TwoAndThreeTreeNode tempNode = new TwoAndThreeTreeNode();

                    newNode.insertData(parent.removeItemData());
                    newNode.connectChildNodes(0,newNode.disconnectChildNodes(1));
                    newNode.connectChildNodes(1,temp2);

                    tempNode.insertData(parent.removeItemData());
                    tempNode.connectChildNodes(0,temp1);
                    tempNode.connectChildNodes(1,node.disconnectChildNodes(0));

                    parent.insertData(node.removeItemData());
                    parent.connectChildNodes(0,tempNode);
                    parent.connectChildNodes(1,newNode);
                } else{
                    itemData = parent.removeItemData();
                    newNode.insertData(parent.removeItemData());
                    newNode.connectChildNodes(0,parent.disconnectChildNodes(0));
                    newNode.connectChildNodes(1,parent.disconnectChildNodes(1));
                    parent.disconnectChildNodes(2);
                    parent.insertData(itemData);
                    parent.connectChildNodes(0,newNode);
                    parent.connectChildNodes(1,node);
                }
                // 进行递归
                connectNode(gParent,parent);
            }
            // 假如父节点为2结点
            else{
                if (data.key.compareTo(parent.getItemData(0).key)<0){
                    TwoAndThreeTreeNode tempNode = parent.disconnectChildNodes(1);
                    parent.connectChildNodes(0,node.disconnectChildNodes(0));
                    parent.connectChildNodes(1,node.disconnectChildNodes(1));
                    parent.connectChildNodes(2,tempNode);
                }else{
                    parent.connectChildNodes(1,node.disconnectChildNodes(0));
                    parent.connectChildNodes(2,node.disconnectChildNodes(1));
                }
                parent.insertData(node.getItemData(0));
            }
        }
    }
}

/**
 * 节点的数据
 */
class Data<Key extends Comparable<Key>, Value> {

    public Key key;
    public Value value;
    //key和value的键值对
    public Data(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public void displayData() {
        System.out.println("/" + key + "---" + value);
    }
}

/**
 * 2-3数节点
 */
class TwoAndThreeTreeNode<Key extends Comparable<Key>,Value>{

    static final int N = 3;
    //当前节点的父节点
    TwoAndThreeTreeNode parent;
    //3个子节点的数组(左子节点，中间子节点和右子节点)
    TwoAndThreeTreeNode[] chirldNodes = new TwoAndThreeTreeNode[N];
    // 当前节点的数据（一个或者两个）
    Data[] itemDatas = new Data[N - 1];
    // 当前节点保存的数据个数
    int itemNum = 0;

    /**
     * 判断是否是叶子结点
     */
    public boolean isLeaf(){

        return chirldNodes[0] == null;
    }

    /**
     * 判断是否已满
     */
    public boolean isFull(){

        return itemNum == N-1;
    }

    /**
     * 获取父节点
     */
    public TwoAndThreeTreeNode getParent(){

        return this.parent;
    }

    /**
     * 连接子节点（左子树，中子树，还是右子树）
     */
    public void connectChildNodes(int index,TwoAndThreeTreeNode child){

        chirldNodes[index] = child;
        if (child != null){
            child.parent = this;
        }
    }

    /**
     * 解除当前节点和其他结点之间的连接
     */
    public TwoAndThreeTreeNode disconnectChildNodes(int index){

        TwoAndThreeTreeNode temp = chirldNodes[index];
        chirldNodes[index] = null;
        return temp;
    }

    /**
     * 获取当前节点的数据(0为左，1为右)
     */
    public Data getItemData(int index){

        return itemDatas[index];
    }

    /**
     * 获得某个位置的子树(0为左指数，1为中子树，2为右子树)
     */
    public TwoAndThreeTreeNode getChildNodes(int index){

        return chirldNodes[index];
    }

    /**
     * 返回当前节点的数据数量，null为-1
     */
    public int getItemDataNum(){

        return itemNum;
    }

    /**
     * 寻找key在当前节点的位置,没有则放回-1
     */
    public int findItem(Key key){

        for (int i = 0; i < itemNum; i++) {
            if (itemDatas[i] == null){
                break;
            }else if (itemDatas[i].key.compareTo(key) == 0){
                return i;
            }
        }
        return -1;
    }

    /**
     * 插入数据(返回插入的位置 0或1)
     */
    public int insertData(Data data){

        itemNum ++;
        for (int i = N -2; i >= 0 ; i--) {
            if (itemDatas[i] == null){
                continue;
            }else{
                if (data.key.compareTo(itemDatas[i].key)<0){
                    itemDatas[i+1] = itemDatas[i];
                }else{
                    itemDatas[i+1] = data;
                    return i+1;
                }
            }
        }
        itemDatas[0] = data;
        return 0;
    }

    /**
     * 移除最后一个键值对（也就是有右边的键值对则移右边的，没有则移左边的）
     */
    public Data removeItemData(){

        Data temp = itemDatas[itemNum - 1];
        itemDatas[itemNum - 1] = null;
        itemNum --;
        return temp;
    }

    /**
     * 显示节点的数据
     */
    public void showAll() {
        for(int i = 0; i < itemNum; i++){
            itemDatas[i].displayData();
        }
        System.out.println("/");
    }
}

