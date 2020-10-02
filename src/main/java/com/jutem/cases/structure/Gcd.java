package com.jutem.cases.structure;

/**
 * 求最大公约数
 */
public class Gcd {
    public int gcd(int n, int m) {
        if(n == m)
            return n;
        else if(n < m)
            return gcd(m, n);
        else
            return gcd(n - m, m);
    }
}
