package com.jutem.cases.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author dawei.chen02@ele.me
 * @create 2020-09-13
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        char[] arr = s.toCharArray();
        Map<Character, Integer> existMap = new HashMap<>();

        int start = 0;
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            Integer index = existMap.get(arr[i]);
            if(index != null && index >= start) {
                max = i - start > max ? i -start : max;
                Integer repeatIndex = existMap.get(arr[i]);
                start = repeatIndex + 1;
            }

            existMap.put(arr[i], i);
        }

        return arr.length - start > max ? arr.length - start : max;
    }
}
