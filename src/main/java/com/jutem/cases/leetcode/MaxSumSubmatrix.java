package com.jutem.cases.leetcode;

/**
 * 矩形区域不超过 K 的最大数值和
 */
public class MaxSumSubmatrix {

    /**
     * 遍历思路类似MaxMatrix
     * @see MaxMatrix
     *
     * 核心问题解决一维数组求不大于k的连续子数组
     * 1.计算前缀和
     * 2.以第n个数字结尾的不超k的连续子数组
     *  2.1 问题转换：f(n) - min(f(i)) < k => f(n) - k < min(f(i))
     *  2.2 f(n)之前的前缀和排序，做二分查找f(n) - k， 从而查找出符合上述条件的min(f(i))
     * 3.求出结果
     *
     * TODO 完成算法
     */
    public int maxSumSubmatrix(int[][] matrix, int k) {

        //求各个行数组的前缀和
        int[][] preSumMatrix = new int[matrix.length][matrix[0].length + 1];
        for (int r = 0; r < preSumMatrix.length; r++) {
            for (int c = 1; c < preSumMatrix[0].length; c++) {
                preSumMatrix[r][c] = matrix[r][c - 1] + preSumMatrix[r][c - 1];
            }
        }

        //开始寻找最大子矩阵
        int rl = preSumMatrix.length;
        int cl = preSumMatrix[0].length;

        //1.框选连续的列范围
        int[] sub = new int[rl];
        int[] result = new int[4];
        int max = Integer.MIN_VALUE;
        for (int c = 1; c < cl; c++) {
            for(int tc = 0; tc < c; tc ++) {
                for(int r = 0; r < rl; r++) {
                    //2.计算子数组每个列子数组和，形成新的一维数组
                    sub[r] = preSumMatrix[r][c] - preSumMatrix[r][tc];
                }
                //3.一维数组求连续最大和
                int[] htm = longestMaxSubSum(sub, k);
                //4。记录结果
                if(max < htm[2]) {
                    result[0] = htm[0];
                    result[1] = tc;
                    result[2] = htm[1];
                    result[3] = c - 1;
                    max = htm[2];
                }
            }
        }

        return max;
    }

    private int[] longestMaxSubSum(int[] a, int k) {
        int last = a[0];
        int max = a[0];
        int h = 0;
        int t = 0;
        int lh = 0;
        for (int i = 1; i < a.length; i++) {
            if(last + a[i] > a[i]) {
                last = last  + a[i];
            } else {
                last = a[i];
                lh = i;
            }

            if(last > max) {
                max = last;
                t = i;
                h = lh;
            }

        }

        return new int[]{h, t, max};
    }

}
