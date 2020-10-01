package com.jutem.cases.leetcode;

/**
 * @author dawei.chen02@ele.me
 * @create 2020-10-01
 */
public class ReverseInteger {

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(reverse(10));
        System.out.println(reverse(0));
        System.out.println(reverse(-10));
        System.out.println(reverse(1534236469));
    }

    public static int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }

    public static int reverseString(int x) {
        boolean validZero = false;
        char[] chars = String.valueOf(x).toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = chars.length - 1; i >= 1; i--) {
            if(chars[i] == '0' && !validZero) {
                continue;
            }
            validZero = true;
            sb.append(chars[i]);
        }

        if(chars[0] == '-') {
            sb.insert(0, '-');
        } else {
            sb.append(chars[0]);
        }


        Long result = Long.valueOf(sb.toString());
        if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        } else {
            return result.intValue();
        }
    }
}
