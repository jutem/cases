package com.jutem.cases.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args) {
        twoSum(new int[]{3,2,4}, 6);
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(nums[i]);
            if(index == null || i > index) {
                map.put(nums[i], i);
            }
        }

        for (int i = 0; i < nums.length; i++) {
            int least = target - nums[i];
            Integer index = map.get(least);
            if(index != null && i != index) {
                return new int[]{i, index};
            }
        }
        return null;
    }

    private int[] firstSolution(int[] nums, int target) {
        int[] cal = Arrays.copyOf(nums, nums.length);
        Arrays.sort(cal);
        for(int head = 0, tail = nums.length - 1; head != tail;) {
            int sum = cal[head] + cal[tail];
            if(sum == target) {
                return new int[]{indexFirst(nums, cal[head]), indexLast(nums, cal[tail])};
            }
            if(sum > target) {
                tail --;
            } else {
                head ++;
            }
        }
        return null;
    }

    private int indexLast(int[] nums, int num) {
        for(int i = nums.length - 1; i >= 0; i --) {
            if(nums[i] == num) {
                return i;
            }
        }
        return 0;
    }

    private int indexFirst(int[] nums, int num) {
        for(int i = 0; i < nums.length; i ++) {
            if(nums[i] == num) {
                return i;
            }
        }
        return 0;
    }

}
