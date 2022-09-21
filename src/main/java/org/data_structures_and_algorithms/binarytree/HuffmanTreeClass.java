package org.data_structures_and_algorithms.binarytree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 哈夫曼编码
 */
class HuffmanCodingClass {
    //编码表
    private Map<Byte, String> huffmanCodingMap = new HashMap<>();
    //压缩的字符数组总长度
    private static int huffmanCodingArrayLength = 0;

    /**
     * 编码结构还原信息文件
     */
    public void restoreFileFromHuffmanCoding(Path distPath, Path savePath) {

        InputStream  in =null;
        ObjectInputStream  ois =null;
        try {
            in = Files.newInputStream(distPath);
            ois = new ObjectInputStream(in);
            //读取文件的字节数组
            byte[] huffmanCodeBytes = (byte[]) ois.readObject();
            byte[] bytes = restoreStringFromHuffmanCoding(huffmanCodeBytes);
            //保存还原后的数据
            Files.write(savePath, bytes);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in!=null){
                    in.close();
                }
                if(ois!=null){
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("还原文件到位置:"+savePath);
    }

    /**
     * 信息文件转换编码结构
     */
    public void getFileIntoHuffmanCoding(Path srcPath, Path distPath) {

        OutputStream os=null;
        ObjectOutputStream oos=null;
        try {
            //获取文件的字节数组
            byte[] bytes = Files.readAllBytes(srcPath);
            //转换编码结构
            byte[] stringIntoHuffmanCoding = getStringIntoHuffmanCoding(bytes);
            // IO流写入字节数组
            os = Files.newOutputStream(distPath);
            oos = new ObjectOutputStream(os);
            // 保存文件内容
            oos.writeObject(stringIntoHuffmanCoding);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(oos!=null){
                    oos.close();
                }
                if(oos!=null){
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件压缩到位置:"+distPath);
    }

    /**
     * 编码结构还原信息字符串
     */
    public byte[] restoreStringFromHuffmanCoding(byte[] bytes) {

        if (bytes != null) {
            List<Byte> resultList = new ArrayList<>();
            //字符数组转二进制
            String huffmanCodingStr = byteToBit(bytes);
            if (huffmanCodingStr == null || huffmanCodingStr.length() <= 0) {
                System.out.println("编码表字符串长度不符合");
                return null;
            }
            //获取编码表Map来对比
            //跳转为Map<String, Byte>,方便后续匹配键值
            Map<String, Byte> codingMap = new HashMap<>();
            huffmanCodingMap.forEach((key, value) -> {
                codingMap.put(value, key);
            });
            //将还原的编码表字符串比对编码表转为数组
            for (int i = 0; i < huffmanCodingStr.length(); ) {
                int count = 1;
                boolean flag = true;
                Byte aByte = null;
                while (flag) {
                    String key = huffmanCodingStr.substring(i, i + count);
                    aByte = codingMap.get(key);
                    //匹配到则退出
                    if (aByte != null) {
                        flag = false;
                        //匹配不到继续截取
                    } else {
                        count++;
                    }
                }
                i = i + count;
                resultList.add(aByte);
            }
            //保存到数组
            byte[] contentBytes = new byte[resultList.size()];
            for (int i = 0; i < resultList.size(); i++) {
                contentBytes[i] = resultList.get(i);
            }
            return contentBytes;
        }
        System.out.println("无效的哈夫曼编码数组");
        return null;
    }

    /**
     * 字符数组转Bit
     */
    private String byteToBit(byte[] bytes) {

        if (huffmanCodingArrayLength == 0) {
            System.out.println("长度不符合还原要求");
            return null;
        }
        //调整字符的二进制位数
        StringBuilder builder = new StringBuilder();
        byte aByte;
        String s;
        for (int i = 0; i < bytes.length; i++) {
            //当最后一个字符时，转换二进制会去除0开头的二进制值，如0111，只留下111，所有需要调整最后一个字符的二进制值位数
            if (i == bytes.length - 1) {
                aByte = bytes[i];
                //根据原先压缩后的编码表字符串长度来补齐二进制值位数
                int totalLength = huffmanCodingArrayLength % 8;
                while (totalLength > 0) {
                    s = "" + (byte) ((aByte >> totalLength - 1) & 0x1);
                    totalLength--;
                    builder.append(s);
                }
                //每个字符转换为二进制值
            } else {
                aByte = bytes[i];
                s = "" + (byte) ((aByte >> 7) & 0x1) +
                        (byte) ((aByte >> 6) & 0x1) +
                        (byte) ((aByte >> 5) & 0x1) +
                        (byte) ((aByte >> 4) & 0x1) +
                        (byte) ((aByte >> 3) & 0x1) +
                        (byte) ((aByte >> 2) & 0x1) +
                        (byte) ((aByte >> 1) & 0x1) +
                        (byte) ((aByte) & 0x1);
                builder.append(s);
            }
        }
        return builder.toString();
    }

    /**
     * 信息字符串转换编码结构
     */
    public byte[] getStringIntoHuffmanCoding(byte[] Bytes) {
        //统计字符串所需的编码
        List<HuffmanCodingNode> nodeList = countHuffmanCoding(Bytes);
        //首节点以下的节点都两两组成新的节点
        while (nodeList.size() > 1) {
            //从小到大排序所有节点
            Collections.sort(nodeList);
            //左右节点组成新节点,新节点的权为值之和
            HuffmanCodingNode left = nodeList.get(0);
            HuffmanCodingNode right = nodeList.get(1);
            HuffmanCodingNode frontNode = new HuffmanCodingNode(null, left.weight + right.weight);
            frontNode.left = left;
            frontNode.right = right;
            nodeList.add(frontNode);
            //删除两个节点
            nodeList.remove(left);
            nodeList.remove(right);
        }
        HuffmanCodingNode node = nodeList.get(0);
        if (node == null) {
            System.out.println("哈夫曼编码为空");
            return null;
        }
        //构建赫夫曼编码表
        StringBuilder builder = new StringBuilder();
        buildHuffmanCoding(node, "", huffmanCodingMap, builder);
        //按照编码表压缩成字符数组
        return getHuffmanCodingIntoByteArray(Bytes, huffmanCodingMap);
    }

    /**
     * 信息字符串按照编码表压缩成字符数组
     */
    private byte[] getHuffmanCodingIntoByteArray(byte[] contentBytes, Map<Byte, String> huffmanCodingMap) {

        StringBuilder builder = new StringBuilder();
        //获取字符对应的编码值
        for (byte contentByte : contentBytes) {
            //连接编码值
            builder.append(huffmanCodingMap.get(contentByte));
        }
        //按照八个编码值为一个字符组成字符数组
        int length = builder.length();
        //计算总的字符数量
        int countByte;
        if (length % 8 == 0) {
            countByte = length / 8;
        } else {
            countByte = length / 8 + 1;
        }
        byte[] bytes = new byte[countByte];
        int index = 0;
        for (int i = 0; i < length; i = i + 8) {
            String huffmanCode;
            //后续超过八个字符时
            if (i + 8 < length) {
                huffmanCode = builder.substring(i, i + 8);
            } else {
                huffmanCode = builder.substring(i);
            }
            //保存字符
            bytes[index++] = (byte) Integer.parseInt(huffmanCode, 2);
        }
        //压缩后保存编码表字符串的长度，用于后面还原为字符串时确认末尾字符的长度
        huffmanCodingArrayLength = builder.length();
        return bytes;
    }

    /**
     * 构建赫夫曼编码表
     */
    private void buildHuffmanCoding(HuffmanCodingNode node, String str, Map<Byte, String> map, StringBuilder builder) {
        // 拼接上一次的字符串
        StringBuilder strBuilder = new StringBuilder(builder);
        strBuilder.append(str);
        //为左右节点组成新节点时，递归编码拼接方向识别字符1或0
        if (node.value == null) {
            buildHuffmanCoding(node.left, "0", map, strBuilder);  //左节点
            buildHuffmanCoding(node.right, "1", map, strBuilder); //右节点
            // 为叶子节点时,以当前字符串作为节点的编码,如10010
        } else {
            map.put(node.value, strBuilder.toString());
        }
    }

    /**
     * 统计字符串所需的编码
     */
    private List<HuffmanCodingNode> countHuffmanCoding(byte[] Bytes) {
        //统计每个字符出现的次数
        Map<Byte, Integer> counts = new HashMap<>();
        //保存字节的ASCII码作为key，出现的次数作为value
        for (byte contentByte : Bytes) {
            Integer count = counts.computeIfAbsent(contentByte, k -> 0);
            counts.put(contentByte, count + 1);
        }
        //保存编码到列表
        List<HuffmanCodingNode> nodeList = new ArrayList<>();
        counts.forEach((key, value) -> {
            nodeList.add(new HuffmanCodingNode(key, value));
        });
        return nodeList;
    }

    public static void main(String[] args) {

        String content = "i like like like java do you like a java java java java";
        byte[] contentBytes = content.getBytes();
        HuffmanCodingClass class1 = new HuffmanCodingClass();
        byte[] huffmanCodingIntoByteArray  = class1.getStringIntoHuffmanCoding(contentBytes);
        byte[] bytes = class1.restoreStringFromHuffmanCoding(huffmanCodingIntoByteArray);
        String restoreString = new String(bytes);
        System.out.println("还原的字符串=" + restoreString);

        HuffmanCodingClass class2 = new HuffmanCodingClass();
        Path srcPath = Paths.get("/Users/user/Desktop/b.bmp");
        Path distPath = Paths.get("/Users/user/Desktop/main.zip");
        class2.getFileIntoHuffmanCoding(srcPath,distPath);
        Path save = Paths.get("/Users/user/Desktop/restore.bmp");
        class2.restoreFileFromHuffmanCoding(distPath,save);
    }
}

/**
 * 哈夫曼树
 */
public class HuffmanTreeClass {

    /**
     * 数组转换数结构
     */
    public static HuffmanTreeNode getHuffmanTree(int[] arr) {

        List<HuffmanTreeNode> nodeList = new ArrayList<>();
        //列表保存所有节点
        for (int i : arr) {
            nodeList.add(new HuffmanTreeNode(i));
        }
        //首节点以下的节点都两两组成新的节点
        while (nodeList.size() > 1) {
            //从小到大排序所有节点
            Collections.sort(nodeList);
            //前面两个节点组成新的节点,新节点的权为值之和
            HuffmanTreeNode left = nodeList.get(0);
            HuffmanTreeNode right = nodeList.get(1);
            HuffmanTreeNode parent = new HuffmanTreeNode(left.value + right.value);
            parent.left = left;
            parent.right = right;
            nodeList.add(parent);
            //删除两个节点
            nodeList.remove(left);
            nodeList.remove(right);
        }
        //返回首节点
        return nodeList.get(0);
    }

    public static void main(String[] args) {

        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        HuffmanTreeNode huffmanTree = getHuffmanTree(arr);
        huffmanTree.preOrder();
    }
}

/**
 * 哈夫曼树节点
 */
class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {

    int value; //节点的权
    HuffmanTreeNode left;
    HuffmanTreeNode right;

    public HuffmanTreeNode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "";
    }

    /**
     * 比较排序
     */
    @Override
    public int compareTo(HuffmanTreeNode o) {
        return this.value - o.value;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {

        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}

/**
 * 哈夫曼编码节点
 */
class HuffmanCodingNode implements Comparable<HuffmanCodingNode> {

    Byte value; // 如果是叶子节点则保存数组的值，如果是非叶子节点则保存左右节点的权值总和
    int weight; // 权值：字符串出现的总次数
    HuffmanCodingNode left;
    HuffmanCodingNode right;

    public HuffmanCodingNode(Byte value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {

        System.out.println(this);
        if (left != null) {
            left.preOrder();
        }
        if (right != null) {
            right.preOrder();
        }
    }

    /**
     * 比较排序
     */
    @Override
    public int compareTo(HuffmanCodingNode o) {

        return weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", weight=" + weight +
                '}';
    }
}
