package org.data_structures_and_algorithms;

/**
 * 汉诺塔
 * 算法设计模式——{
 * if |P| ≤ n0
 *   then return (ADHOC(P))
 * for i ← to k                   // 将 P 分解为较小的子问题 P1,P2...Pk
 * do yi ← Divide-and-Conquer(Pi) // 递归解决 pi
 * T ← MERGE(y1,y2,..yk)          // 合并子问题
 * return(T) }
 */
public class HanoitowerClass {

    /**
     * 将A塔的盘移动到C塔
     */
    public static void adjustTower(int num, char ATower, char BTower, char CTower){

        if (num == 1) {
            //每个盘最后一步到达C塔
            System.out.printf("第 %d 个盘从 %s → %s \n", num, ATower, CTower);
        /* 有三个盘的时候：
            A → C
            A → B
            C → B
            A → C
            B → A
            B → C
            A → C
        */
        //按照规律构建回归
        } else {
            //CTower和BTower在每一轮中交替位置
            adjustTower(num - 1, ATower, CTower, BTower);
            System.out.printf("第 %d 个盘从 %s → %s \n", num, ATower, CTower);
            adjustTower(num - 1, BTower, ATower, CTower);
        }
    }

    public static void main(String[] args) {

        adjustTower(2, 'A', 'B', 'C');
    }
}
