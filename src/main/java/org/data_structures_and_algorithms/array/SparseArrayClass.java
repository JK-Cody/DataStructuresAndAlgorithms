package org.data_structures_and_algorithms.array;

/**
 * 稀疏数组
 */
public class SparseArrayClass {

    final int countRow=3; //列数,每行固定多少值

    /**
     * 二维数组转换为稀疏数组
     */
    public int[][] transferCheckerToSparseArray(int [] [] checker){

        int valueRow=1; //行数，保留首行来记录有多少个非0值
        int rowA=checker.length;
        int rouwB=0;
//一次遍历
        for (int[] ints : checker) {
            rouwB=ints.length;
            for (int anInt : ints) {
                if(anInt!=0){
                    //记录非0值数量
                    valueRow++;
                }
                System.out.printf("%d\t",anInt);
            }
            System.out.println();
        }
        System.out.println("********************************");
//创建稀疏数组
        int[][] sparseArray=new int[valueRow][countRow];
        sparseArray[0][0]=rowA;
        sparseArray[0][1]=rouwB;
        sparseArray[0][2]=countRow;
//二次遍历
        int index=1;//稀疏数组起点
        for (int i = 0; rowA>i; i++) {
            for (int j = 0; rouwB>j; j++) {
                if(checker[i][j]!=0){
                    sparseArray[index][0]=i;
                    sparseArray[index][1]=j;
                    sparseArray[index][2]=checker[i][j];
                    index++;
                }
            }
        }

        for (int[] ints : sparseArray) {
            for (int anInt : ints) {
                System.out.printf("%d\t",anInt);
            }
            System.out.println();
        }
        return sparseArray;
    }

    /**
     * 稀疏数组转换为二维数组
     */
    public int[][] transferSparseArrayToChecker(int [] [] sparseArray){

        int [] [] checker = new int[sparseArray[0][0]][sparseArray[0][1]];
        for(int i = 1; i <= sparseArray[0][2];i++){
            checker[sparseArray[i][0]][sparseArray[i][1]]=sparseArray[i][2];
        }

        for (int[] ints : checker) {
            for (int anInt : ints) {
                System.out.printf("%d\t",anInt);
            }
            System.out.println();
        }
        return checker;
    }


    public static void main( String[] args ) {

        int [] [] checker = new int[12][11];
        checker[2][4]=1;
        checker[3][5]=3;
        checker[6][6]=6;
        SparseArrayClass sparseArrayClass = new SparseArrayClass();
        int[][] sparseArray = sparseArrayClass.transferCheckerToSparseArray(checker);
        System.out.println("********************************");
        sparseArrayClass.transferSparseArrayToChecker(sparseArray);
    }

}
