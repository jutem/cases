package com.jutem.cases.leetcode;

import sun.jvm.hotspot.gc_implementation.parallelScavenge.PSYoungGen;

/**
 * @author dawei.chen02@ele.me
 * @create 2020-09-20
 */
public class LongestPalindromicSubstring {


    //abbc bb
    public static String longestPalindrome(String s) {
        if (s.length() == 1 || s.length() == 0) {
            return s;
        }

        String subStr = "";
        for (float i = 0.5f; i <= s.length() - 1.5; i += 0.5) {

            int left = (int) Math.ceil(i - 1), right = (int) Math.floor(i + 1);
            for (; left >= 0 && right < s.length(); left--, right++) {
                if (s.charAt(left) != s.charAt(right)) {
                    break;
                }
            }

            String str = s.substring(left + 1, right);
            if (str.length() > subStr.length()) {
                subStr = str;
            }
        }

        if (subStr.length() == 0) {
            return s.substring(0, 1);
        }

        return subStr;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("a"));
        System.out.println("12345678".substring(0, 8));
        System.out.println(Math.ceil(1) + " : " + Math.floor(1.5));

    }
}
