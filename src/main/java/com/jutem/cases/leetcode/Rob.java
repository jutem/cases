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

    /**
     * 环形数组rob
     * [2,3,2] 输出3
     * 所以如果是环形数组的话，实际上是增加了更多偷窃的限制
     */
    public int robLoop(int[] nums) {
        if(nums.length == 0) {
            return 0;
        }

        int last1 = 0;
        int last2 = 0;
        int max = Integer.MIN_VALUE;
        boolean firstRobed = false;
        for (int i = 0; i < nums.length; i++) {
            if(i != nums.length - 1) {
                int now = Math.max(nums[i] + last2, last1);
                max = Math.max(now, max);
                last2 = last1;
                last1 = now;
            } else {
                //如果是最后一个点，需要查看last2的方案里是否已经存在了i=0的点
                if(nums[i] + last2 > last1) {
                    if(!firstRobed) {
                        max = Math.max(nums[i] + last2, max);
                    } else {
                        int now = Math.max(last2, last1);
                        max = Math.max(now, max);
                    }
                } else {
                    max = Math.max(last1, max);
                }
            }
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
