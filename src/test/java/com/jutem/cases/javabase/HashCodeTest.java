package com.jutem.cases.javabase;

import org.junit.Test;

public class HashCodeTest {
    @Test
    public void hashcodeTest() {
        HashCode hashCode = new HashCode();
        HashCode beforeQuote = hashCode;
        int beforeHashCode = beforeQuote.hashCode();

        hashCode.setI(10);
        HashCode afterQuote = hashCode;
        int afterHashCode = afterQuote.hashCode();

        System.out.println(beforeHashCode + " | " + afterHashCode);
        System.out.println(beforeQuote.equals(afterQuote));
    }

    @Test
    public void lombokHashCodeTest() {
        LombokHashCode hashCode = new LombokHashCode();
        LombokHashCode beforeQuote = hashCode;
        int beforeHashCode = beforeQuote.hashCode();

        hashCode.setI(10);
        LombokHashCode afterQuote = hashCode;
        int afterHashCode = afterQuote.hashCode();
        System.out.println(beforeHashCode + " | " + afterHashCode);
        System.out.println(beforeQuote.equals(afterQuote));
    }
}
