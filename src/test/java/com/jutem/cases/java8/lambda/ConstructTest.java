package com.jutem.cases.java8.lambda;

import org.junit.Test;

public class ConstructTest {
    @Test
    public void construct() {
        Construct<Person> construct = Person::new;
    }
}

