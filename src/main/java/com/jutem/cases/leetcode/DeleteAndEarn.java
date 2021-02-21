package com.jutem.cases.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dawei.chen02@ele.me
 * @create 2021-02-21
 */
public class DeleteAndEarn {
    /**
     * 问题可以转化为打家劫舍问题
     * @see Rob
     * 1.排序数组
     * 2.相同的数字分组求和，获得一个新的数组, 如果不相邻的两个数字之间插入0
     * 3.这个数组每一位代表一个房子里的钱财（分数），相邻的房子不能连续偷取
     */
    public int deleteAndEarn(int[] nums) {
        //构造
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        int before = 0;
        int sum = 0;
        for (int num : nums) {
            if(num == before) {
                sum += num;
            } else if(num - 1 == before) {
                if(sum != 0) {
                    list.add(sum);
                    sum = 0;
                }
                sum = num;
            } else {
                if(sum != 0) {
                    list.add(sum);
                    sum = 0;
                }
                list.add(0);
                sum = num;
            }

            before = num;
        }
        list.add(sum);


        Integer max = 0;
        Integer k1 = 0;
        Integer k2 = 0;
        for (Integer num : list) {
            max = k2 + num > k1 ? k2 + num : k1;
            k2 = k1;
            k1 = max;
        }
        return max;
    }
}
