package com.jutem.cases.leetcode;

import org.springframework.util.Assert;

import java.util.Arrays;

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
     * tips : 可以直接正向求，一维数组的最大不超过k的子序和，不用转为前缀和，二分查找
     */
    public Integer maxSumSubmatrix(int[][] matrix, int k) {

        //求各个行数组的前缀和
        int[][] preSumMatrix = new int[matrix.length][matrix[0].length + 1];
        for (int r = 0; r < preSumMatrix.length; r++) {
            preSumMatrix[r] = preSum(matrix[r]);
        }

        //开始寻找最大子矩阵
        int rl = preSumMatrix.length;
        int cl = preSumMatrix[0].length;

        //1.框选连续的列范围
        int[] sub = new int[rl];
        Integer max = null;
        for (int c = 1; c < cl; c++) {
            for(int tc = 0; tc < c; tc ++) {
                for(int r = 0; r < rl; r++) {
                    //2.计算子数组每个列子数组和，形成新的一维数组
                    sub[r] = preSumMatrix[r][c] - preSumMatrix[r][tc];
                }
                //3.一维数组求连续最大和
                Integer subMax = longestKMaxSubSum(sub, k);
                //4。记录结果
                if(max == null || (subMax != null && max < subMax)) {
                    max = subMax;
                }
            }
        }

        return max == null ? 0 : max;
    }

    //一维数组求不大于k的连续子数组
    private Integer longestKMaxSubSum(int[] a, int k) {

        int[] preSum = preSum(a);

        Integer max = null;
        for (int i = 1; i < preSum.length; i++) {
            int tag = preSum[i] - k;
            Arrays.sort(preSum, 0, i - 1);
            Integer legal = binarySearch(preSum, 0, i - 1, tag, i -1);

            if(legal != null && (max == null || preSum[i] - legal > max)) {
                max = preSum[i] - legal;
            }
        }

        return max;
    }

    /**
     * 查询不小于minKey的最小值，不存在则返回null
     */
    private Integer binarySearch(int[] a, int left, int right, int minKey, int maxRight) {
        int mid = left + (right - left) / 2;
        if(a[mid] > minKey) {
            if(left > mid - 1) {
                return a[mid];
            } else {
                return binarySearch(a, left, mid - 1, minKey, maxRight);
            }
        }
        if(a[mid] < minKey) {
            if(mid + 1 > right) {
                if(mid + 1 <= maxRight) {
                    return a[mid + 1];
                } else {
                    return null;
                }
            } else {
                return binarySearch(a, mid + 1, right, minKey, maxRight);
            }
        }
        return a[mid];
    }

    //计算前缀和
    private int[] preSum(int[] a) {
        int[] preSum = new int[a.length + 1];
        for (int c = 1; c < preSum.length; c++) {
            preSum[c] = a[c - 1] + preSum[c - 1];
        }
        return preSum;

    }

    public static void main(String[] args) {

        MaxSumSubmatrix max = new MaxSumSubmatrix();

        int r = max.maxSumSubmatrix(new int[][]{{1,0,1},{0,-2,3}}, 2);
        Assert.isTrue(2 == r);

        r = max.maxSumSubmatrix(new int[][]{{27,5,-20,-9,1,26,1,12,7,-4,8,7,-1,5,8},{16,28,8,3,16,28,-10,-7,-5,-13,7,9,20,-9,26},{24,-14,20,23,25,-16,-15,8,8,-6,-14,-6,12,-19,-13},{28,13,-17,20,-3,-18,12,5,1,25,25,-14,22,17,12},{7,29,-12,5,-5,26,-5,10,-5,24,-9,-19,20,0,18},{-7,-11,-8,12,19,18,-15,17,7,-1,-11,-10,-1,25,17},{-3,-20,-20,-7,14,-12,22,1,-9,11,14,-16,-5,-12,14},{-20,-4,-17,3,3,-18,22,-13,-1,16,-11,29,17,-2,22},{23,-15,24,26,28,-13,10,18,-6,29,27,-19,-19,-8,0},{5,9,23,11,-4,-20,18,29,-6,-4,-11,21,-6,24,12},{13,16,0,-20,22,21,26,-3,15,14,26,17,19,20,-5},{15,1,22,-6,1,-9,0,21,12,27,5,8,8,18,-1},{15,29,13,6,-11,7,-6,27,22,18,22,-3,-9,20,14},{26,-6,12,-10,0,26,10,1,11,-10,-16,-18,29,8,-8},{-19,14,15,18,-10,24,-9,-7,-19,-14,23,23,17,-5,6}}, -100);
        Assert.isTrue(-101 == r);
    }

}
