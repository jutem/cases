package com.jutem.cases.leetcode;

import java.util.*;

/**
 * @author dawei.chen02@ele.me
 * @create 2020-10-18

 */
public class LIS {
    /**
     * 最长上升子序列
     * 输入: [10,9,2,5,3,7,101,18]
     * 输出: 4
     * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
     */
    public int lengthOfLIS(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        TreeMap<Integer, Integer> map = new TreeMap<>(Comparator.comparing(i -> -i));
        for (int i = 0; i < nums.length; i++) {
            if(i == 0) {
                map.put(1, nums[i]);
                continue;
            }

            boolean biggest = true;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if(entry.getValue() < nums[i]) {
                    map.put(entry.getKey() + 1, nums[i]);
                    biggest = false;
                    break;
                }
            }

            if(biggest) {
                map.put(1, nums[i]);
                continue;
            }

        }
        return map.firstKey();
    }

    /**
     * 最长递增子序列的个数
     */
    public int findNumberOfLIS(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        List<Pair> pairs = new ArrayList<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            Pair now = new Pair(1, 1, nums[i]);
            for (Pair pair : pairs) {
                if(pair.value < now.value) {
                    if(pair.length + 1 == now.length) {
                        now.count += pair.count;
                    }
                    if(pair.length + 1 > now.length) {
                        now.length = pair.length + 1;
                        now.count = pair.count;
                    }
                }
            }
            pairs.add(now);
        }

        int max = pairs.stream().max(Comparator.comparingInt(p -> p.length)).get().length;
        return pairs.stream().filter(p -> p.length == max).map(p -> p.count).reduce(0, (i1, i2) -> i1 + i2);
    }

    private class Pair {
        int length;
        int count;
        int value;

        public Pair(int length, int count, int value) {
            this.length = length;
            this.count = count;
            this.value = value;
        }
    }

    /**
     * 环形子组数的最大和
     * 输入：[3,-1,2,-1]
     * 输出：4
     * 解释：从子数组 [2,-1,3] 得到最大和 2 + (-1) + 3 = 4
     */
    public int maxSubarraySumCircular(int[] A) {

        int[] preSum = new int[A.length * 2 + 1];
        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + A[(i - 1) % A.length];
        }

        Deque<Integer> q = new ArrayDeque();
        q.addFirst(0);
        int max = A[0];
        for (int i = 1; i < preSum.length; i++) {
            if(i - q.getFirst() > A.length) {
                q.pollFirst();
            }
            if(!q.isEmpty()) {
                max = Math.max(max, preSum[i] - preSum[q.peekFirst()]);
            }
            while (!q.isEmpty() && preSum[q.peekLast()] >= preSum[i]) {
                q.pollLast();
            }
            q.offerLast(i);
        }

        return max;
    }

    public static void main(String[] args) {
        LIS l = new LIS();
        System.out.println("test:" + l.maxSubarraySumCircular(new int[]{-2,-3,-1}));
        System.out.println("test:" + l.maxSubarraySumCircular(new int[]{-2}));
        System.out.println("test:" + l.maxSubarraySumCircular(new int[]{3,1,3,2,6}));
    }


}
