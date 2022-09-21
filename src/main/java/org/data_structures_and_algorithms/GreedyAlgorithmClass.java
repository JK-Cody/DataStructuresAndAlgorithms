package org.data_structures_and_algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 贪心算法
 */
public class GreedyAlgorithmClass {

    /**
     * 贪心算法获取每轮的最优解
     */
    public static Set<String> greedy(Map<String, Set<String>> selectElement) {
        //获取所需的元素
        Set<String> allNeedElement = new HashSet<>();
        selectElement.forEach((k, v) -> {
            allNeedElement.addAll(v);
        });
        System.out.println("所需的元素：" + allNeedElement);
        Set<String> selects = new HashSet<>();
        Set<String> temp = new HashSet<>();
        String maxKey = null; //每次比较时，可涵盖最多所需元素的选择
        int maxKeyNumber = 0; // maxKey可涵盖的元素数量
        //每轮获取涵盖的所需的元素的最多数量的选择
        while (!allNeedElement.isEmpty()) {
            for (String key : selectElement.keySet()) {
                temp.addAll(selectElement.get(key));
                //获取交集
                temp.retainAll(allNeedElement);
                // 比较最多数量的选择，如果相同则选择排前的选择
                if (temp.size() > 0 && temp.size() > maxKeyNumber) {
                    maxKey = key;
                    maxKeyNumber = temp.size();
                }
                temp.clear();
            }
            //没有交集时
            if (maxKey == null) {
                continue;
            }
            //保存最多数量的选择
            selects.add(maxKey);
            //从所需的元素删除此选择涵盖的元素
            allNeedElement.removeAll(selectElement.get(maxKey));
            maxKey = null;
            maxKeyNumber = 0;
        }
        return selects;
    }

    public static void main(String[] args) {

        Map<String, Set<String>> broadcasts = new HashMap<>();
        Set<String> k1 = new HashSet<>();
        k1.add("北京");
        k1.add("上海");
        k1.add("天津");
        Set<String> k2 = new HashSet<>();
        k2.add("广州");
        k2.add("北京");
        k2.add("深圳");
        Set<String> k3 = new HashSet<>();
        k3.add("成都");
        k3.add("上海");
        k3.add("杭州");
        Set<String> k4 = new HashSet<>();
        k4.add("上海");
        k4.add("天津");
        Set<String> k5 = new HashSet<>();
        k5.add("杭州");
        k5.add("大连");

        broadcasts.put("k1", k1);
        broadcasts.put("k2", k2);
        broadcasts.put("k3", k3);
        broadcasts.put("k4", k4);
        broadcasts.put("k5", k5);

        Set<String> greedy = greedy(broadcasts);
        System.out.println("选择好的电台列表：" + greedy);
    }
}
