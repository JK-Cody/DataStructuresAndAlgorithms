package org.data_structures_and_algorithms;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 马踏棋盘算法(骑士周游问题)
 */
public class HorseChessboardClass {

    int X; // 棋盘的行数
    int Y; // 棋盘的列数
    private boolean finished;  //找到解法的结束标识
    private  boolean[] checked;

    public HorseChessboardClass(int X,int Y){

        this.X=X;
        this.Y=Y;
        this.checked=new boolean[X * Y];
    }

    /**
     * 马踏棋盘算法
     */
    public void getChessboard(int[][] chessboard, int cx, int cy, int step) {

        this.X=chessboard.length;
        this.Y=chessboard[0].length;
        //标识起始点已经访问
        checked[buildCheckedIndex(cx, cy)] = true;
        //标识步数
        chessboard[cx][cy] = step;
        //获取棋盘上一点的下一个可以移动的点坐标
        ArrayList<Point> pointList = nextStep(new Point(cx, cy));
        //排序选择的下一个移动的点的优先度
        sortPointList(pointList);
        //对所有下一个可以移动的点坐标进行测试
        while (!pointList.isEmpty()) {
            Point point = pointList.remove(0);
            if (!checked[buildCheckedIndex(point.x, point.y)]) {
                //回溯到下一个移动的点
                getChessboard(chessboard, point.x, point.y, step + 1);
            }
        }
        //如果该点和后续的点的走法不成功时
        if (step < X * Y && !finished) {
            //修改该点没有被访问
            checked[buildCheckedIndex(cx, cy)] = false;
            //步数清零
            chessboard[cx][cy] = 0;
        } else {
            finished = true;  // 表示已经完成任务
        }
    }

    /**
     * 计算点坐标属于棋盘的第几个点
     */
    private int buildCheckedIndex(int cx, int xy) {

        return cx * X + xy;
    }

    /**
     * 优化效率,排序所有对下一个可以移动的点
     */
    private void sortPointList(ArrayList<Point> pointList) {

        pointList.sort((o1, o2) -> {
            ArrayList<Point> next1 = nextStep(o1);
            ArrayList<Point> next2 = nextStep(o2);
            return Integer.compare(next1.size(), next2.size());
        });
    }

    /**
     * 获取棋盘上一点的下一个可以移动的点坐标
     * 从 第5 ~ 7开始，然后 7 ~ 0
     */
    public ArrayList<Point> nextStep(Point current) {

        ArrayList<Point> result = new ArrayList<>(); //坐标数组
        int cx = current.x;
        int cy = current.y;
        //保存每个可以移动的坐标
        if (cx - 1 >= 0 && cy - 2 >= 0) {
            result.add(new Point(cx - 1, cy - 2));
        }
        if (cx - 2 >= 0 && cy - 1 >= 0) {
            result.add(new Point(cx - 2, cy - 1));
        }
        if (cx - 2 >= 0 && cy + 1 < Y) {
            result.add(new Point(cx - 2, cy + 1));
        }
        if (cx - 1 >= 0 && cy + 2 < Y) {
            result.add(new Point(cx - 1, cy + 2));
        }
        if (cx + 1 < X && cy + 2 < Y) {
            result.add(new Point(cx + 1, cy + 2));
        }
        if (cx + 2 < X && cy - 1 >= 0) {
            result.add(new Point(cx + 2, cy - 1));
        }
        if (cx + 2 < X && cy + 1 < Y) {
            result.add(new Point(cx + 2, cy + 1));
        }
        if (cx + 1 < X && cy - 2 >= 0) {
            result.add(new Point(cx + 1, cy - 2));
        }
        return result;
    }

    public static void main(String[] args) {

        int[][] chessboard = new int[8][8];
        HorseChessboardClass horseChessboardClass = new HorseChessboardClass(8,8);
        horseChessboardClass.getChessboard(chessboard, 0, 0, 1);
        System.out.println("按照序号行走:");
        for (int[] row : chessboard) {
            System.out.println(Arrays.toString(row));
        }
    }
}
