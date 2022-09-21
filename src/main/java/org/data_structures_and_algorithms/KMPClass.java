package org.data_structures_and_algorithms;

import java.util.Arrays;

/**
 * KMP算法
 */
public class KMPClass {

    /**
     * KMP匹配
     */
    public static int KMPMatch(String str, String targetStr){

        char[] chars = str.toCharArray();
        char[] targetChars = targetStr.toCharArray();
        int i = 0; // 指向 str中正在匹配的位置
        int j = 0; // 指向 targetStr中正在匹配的位置
        //获取部分匹配值
        int[] matchValue = getKMPMatchValue(targetStr);
        while (i < chars.length && j < targetChars.length) {
            //该字符匹配成功时
            if (chars[i] == targetChars[j]) {
                i++;
                j++;
            //匹配不成功时
            } else {
                //按照部分匹配值回到有匹配值的位置(字符串长度 - 长度对应的部分匹配值)
                if(j>0) {
                    int retreat = matchValue[j - 1];
                    i = i - retreat;
                    //回到targetStr的起点
                    j = 0;
                    continue;
                }
                i++;
            }
        }
        //匹配到完整的字符串
        if (j==targetChars.length) {
            int index = i - j;
            System.out.println("从第{"+index+"}开始匹配到");
            return index;
        }
        System.out.println("没有匹配到");
        return -1;
    }

    /**
     * 获取目标字符串的部分匹配值
     */
    private static int[] getKMPMatchValue(String targetStr){

        int length = targetStr.length();
        int[] matchValue=new int[length];
        char prefix;
        char suffix;
        char[] targetChars = targetStr.toCharArray();
/* 方式一 */
        // i决定前缀的最长的索引，j决定后缀的最长的索引，两者相差总是1
        for (int j = 1, i = 0; j < length; j++) {
            prefix=targetChars[i];
            suffix=targetChars[j];
            //没有连续匹配时
            while (i > 0 && suffix != prefix) {
                /* i表示前缀的前i个字符与后缀有匹配，至少需要满足前缀有i+1个字符，因为matchValue和targetChars长度相等，
                   i-1表示第i个字符,此时前缀和后缀没有匹配值，所以matchValue[i - 1]为0;
                   也有可能 matchValue[i - 1]为非0，如:AAA,此时matchValue[i - 1]仍然可视为0 */
                i = matchValue[i - 1];  //可以直接设为0
            }
            //因为后缀总是先匹配到前缀的第一个字符，才能匹配下一个字符，因此后缀递增字符，直到获得第一个匹配字符
            prefix=targetChars[i];
            //匹配时
            if (suffix == prefix){
                i++;
            }
            //部分匹配值第一个时只有前缀没有后缀，因此从1开始保存匹配值
            matchValue[j] = i;
        }

/* 方式二 */
        // i决定前缀的最长的索引，j决定后缀的最长的索引，两者相差总是1
//        for (int i=0,j=1;j<length;j++) {
//            prefix = targetChars[i];
//            suffix=targetChars[j];
//            //因为后缀总是先匹配到前缀的第一个字符，才能匹配下一个字符，因此后缀递增字符，直到获得第一个匹配字符
//            while (prefix==suffix){
//                //部分匹配值第一个时只有前缀没有后缀，因此从1开始保存匹配值
//                i++;
//                matchValue[j]=i;
//                //前缀递增字符
//                prefix = targetChars[i];
//                //后缀递增字符
//                j++;
//                suffix=targetChars[j];
//            }
//        }
        System.out.println("部分匹配值="+Arrays.toString(matchValue));
        return matchValue;
    }

    /**
     * 暴力匹配
     */
    public static int violenceMatch(String str, String targetStr) {

        char[] chars = str.toCharArray();
        char[] targetChars = targetStr.toCharArray();
        int i = 0; // 指向 s1中正在匹配的位置
        int j = 0; // 指向 s2中正在匹配的位置

        while (i < chars.length && j < targetChars.length) {
            //该字符匹配成功时
            if (chars[i] == targetChars[j]) {
                i++;
                j++;
            //匹配不成功时
            } else {
                //回到逐渐匹配str成功时的位置的后一格
                i = i - j + 1;
                //回到targetStr的起点
                j = 0;
            }
        }
        //匹配到完整的字符串
        if (j==targetChars.length) {
            int index = i - j;
            System.out.println("从第{"+index+"}开始匹配到");
            return index;
        }
        System.out.println("没有匹配到");
        return -1;
    }

    public static void main(String[] args) {

        String str1 = "BBC ABCDAAAB CDABAAABCABCDDABCADABAABABBDE";
        String str2 = "ABCABCDDABC";
        violenceMatch(str1, str2);
        KMPMatch(str1, str2);
    }
}
