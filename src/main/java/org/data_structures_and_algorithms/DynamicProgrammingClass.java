package org.data_structures_and_algorithms;

import lombok.ToString;

import java.util.*;

/**
 * 动态规划
 */
public class DynamicProgrammingClass {

    /**
     * 背包问题,物品不重复，不超过最大重量时的最高价格
     */
    public static void KnapsackProblem(List<Cart> cartList, int maxWeight) {
/* 方式一 :填表法 */
        int size = cartList.size();
        //多一位留给没有物品时，即0
        int[] priceArray = new int[size+1];
        int[] weightArray = new int[size+1];

        priceArray[0]=0;
        weightArray[0]=0;
        int count=1;
        //将物品的内容转移到数组
        Iterator<Cart> iterator = cartList.iterator();
        while (iterator.hasNext()){
            Cart cart = iterator.next();
            priceArray[count]=cart.price;
            weightArray[count]=cart.weight;
            count++;
        }
        /* v[i][j] 表示在前 i 个物品中能够装入容量为 j 的背包中的最大价值 */
        //多一位留给没有重量时，即0
        int[][] priceWithWeight = new int[priceArray.length][maxWeight + 1];
        // 用于存放每个格子中保存的商品
        int[][] carts = new int[priceArray.length][maxWeight + 1];
        // 第一行默认重量0，价值0，从1开始
        for (int i = 1; i < priceWithWeight.length; i++) {
            //从小容量开始获取最佳的存放物品价值，直到最大容量
            for (int j = 1; j < priceWithWeight[0].length; j++) {
                //当前容量超过时
                if (weightArray[i] > j) {
                    //使用上一个物品的容量,保存上一个存放物品价值作为当前物品
                    priceWithWeight[i][j] = priceWithWeight[i - 1][j];
                //有空余空间时
                }else {
                    int pre = priceWithWeight[i - 1][j];
                    // 上一个存放物品价值的未使用物品价值
                    int vacation = priceWithWeight[i - 1][j - weightArray[i]];
                    int curr = priceArray[i];
                    //保存当前容量时更多的物品价值
                    priceWithWeight[i][j] = Math.max(pre, priceArray[i] + vacation);
                    if (pre < curr + vacation) {
                        priceWithWeight[i][j] = curr + vacation;
                        carts[i][j] = 1;
                    } else {
                        priceWithWeight[i][j] = pre;
                    }
                }
            }
        }
        //输出数组内容
        int i = carts.length - 1; // 行的最大下标
        int j = carts[0].length - 1; // 列的最大下标
        while (i > 0 && j > 0) {
            if (carts[i][j] == 1) {
                System.out.printf("商品数量:重量 [%d:%d] \n", i, j);
                j = j - weightArray[i];
            }
            // 找完一行，则减少一行
            i--;
        }

/* 方式二 */
//        List<String> stringList = new LinkedList<>();
//        List<Integer> weightList = new LinkedList<>();
//        List<Integer> priceList= new LinkedList<>();
//
//        for (Cart cart : cartList) {
//            //数组没有值时,初始化第一个值
//            int size = weightList.size();
//            if (size == 0) {
//                if (cart.weight <= maxWeight) {
//                    weightList.add(cart.weight);
//                    stringList.add(cart.name);
//                    priceList.add(cart.price);
//                }
//                continue;
//            }
//            //数组有值时
//            int i = size; //保留当前有重量的数组元素个数
//            int j = 0;
//            //将当前数组元素与已有的元素拼接重量,产生新的元素
//            while (j < i) {
//                int totalWeight = cart.weight + weightList.get(j);
//                if (totalWeight <= maxWeight) {
//                    weightList.add(totalWeight);
//                    stringList.add(stringList.get(j)+"-"+cart.name);
//                    priceList.add(priceList.get(j)+cart.price);
//                }
//                j++;
//            }
//            //保存当前数组元素
//            if (cart.weight <= maxWeight) {
//                weightList.add(cart.weight);
//                stringList.add(cart.name);
//                priceList.add(cart.price);
//            }
//        }
//        int maxPrice=priceList.get(0);
//        int index=0;
//        for (int i=1;i<priceList.size();i++){
//            Integer price = priceList.get(i);
//            if(price>maxPrice){
//                maxPrice=price;
//                index=i;
//            }
//        }
//        System.out.println("组合名字="+stringList.get(index)+"|"+"组合总价="+maxPrice+"|"+"组合总重量="+weightList.get(index));
    }

    public static void main(String[] args) {

        Cart cart1=new Cart("吉他",1,1500);
        Cart cart2=new Cart("电脑",3,3000);
        Cart cart3=new Cart("音响",4,2000);
        Cart cart4=new Cart("音响x",2,1000);
        List<Cart> cartList=new ArrayList<>();
        cartList.add(cart1);
        cartList.add(cart2);
        cartList.add(cart3);
        cartList.add(cart4);
        int maxWeight=8;
        KnapsackProblem(cartList,maxWeight);
    }
}

/**
 * 背包内的物品
 */
@ToString
class Cart{

    String name;
    int weight;
    int price;

    public Cart(String name,int weight,int price){

        this.name=name;
        this.weight=weight;
        this.price=price;
    }
}