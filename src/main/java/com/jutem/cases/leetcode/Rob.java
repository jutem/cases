package com.jutem.cases.leetcode;

import java.util.Arrays;

/**
 * @author dawei.chen02@ele.me
 * @create 2020-12-20
 */
public class Rob {
    /**
     * dp(n)表示偷取第n个屋子钱为前提，最多能偷到多少
     * dp(n) = nums[n] + max(dp(n -2), dp(n-3))
     */
    public int rob(int[] nums) {

        if(nums.length == 0) {
            return 0;
        }

        int[] dp = new int[nums.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {

            int last2 = i - 2 >= 0 ? dp[i - 2] : 0;
            int last3 = i - 3 >= 0 ? dp[i - 3] : 0;

            int fi = nums[i] + Math.max(last2, last3);
            max = Math.max(fi, max);

            dp[i] = fi;
        }
        return max;
    }

    /**
     * 更优雅的方式
     * dp(n) 表示数组长度为n，最多能偷到多少
     * dp(n) = max(nums[n] + dp[n - 2], dp[n -1])
     */
    public int rob2(int[] nums) {

        if(nums.length == 0) {
            return 0;
        }

        int last1 = 0;
        int last2 = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int now = Math.max(nums[i] + last2, last1);
            max = Math.max(now, max);
            last2 = last1;
            last1 = now;
        }

        return max;
    }

    /**
     * 环形数组rob
     * [2,3,2] 输出3
     * 所以如果是环形数组的话，实际上是增加了更多偷窃的限制
     * 解题思路：
     * 成环以后分类讨论
     * 三种情况：
     * 1.不【考虑】头尾元素， 2.【考虑】头元素，【不考虑】尾元素，3.【考虑】尾元素，【不考虑】头元素
     * 由于23情况包含情况1，所以我们只考虑2，3即可
     */
    public int robLoop(int[] nums) {
        if(nums.length == 0) {
            return 0;
        }
        if(nums.length == 1) {
            return nums[0];
        }
        int r1 = rob2(Arrays.copyOfRange(nums, 0, nums.length - 1));
        int r2 = rob2(Arrays.copyOfRange(nums, 1, nums.length));
        return Math.max(r1, r2);
    }

    public static void main(String[] args) {
        Rob rob = new Rob();
        int r = rob.rob(new int[]{1,2,3,1});
        System.out.println(r);
        r = rob.rob2(new int[]{1,2,3,1});
        System.out.println(r);
    }
}
