package com.jutem.cases.java8.lambda;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author dawei.chen02@ele.me
 * @create 2019-09-28
 */
public class ToListCollector implements Collector{
    /**
     * @return
     */
    @Override
    public Supplier supplier() {
        return null;
    }

    @Override
    public BiConsumer accumulator() {
        return null;
    }

    @Override
    public BinaryOperator combiner() {
        return null;
    }

    @Override
    public Function finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
