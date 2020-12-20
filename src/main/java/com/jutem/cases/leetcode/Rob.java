package com.jutem.cases.leetcode;

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

    public static void main(String[] args) {
        Rob rob = new Rob();
        int r = rob.rob(new int[]{1,2,3,1});
        System.out.println(r);
        r = rob.rob2(new int[]{1,2,3,1});
        System.out.println(r);
    }
}
