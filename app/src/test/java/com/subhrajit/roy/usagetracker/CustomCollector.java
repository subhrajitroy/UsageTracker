package com.subhrajit.roy.usagetracker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

class CustomCollector implements Collector<Integer, List<Integer>,List<Integer>> {


    private Integer multiplier;

    CustomCollector(Integer multiplier){
        this.multiplier = multiplier;
    }

    private void print(Object obj){
        System.out.println(obj);
    }

    @Override
    public Supplier<List<Integer>> supplier() {
        print("In supplier");
        return () -> new ArrayList<>();
    }

    @Override
    public BiConsumer<List<Integer>, Integer> accumulator() {
        print("In accumulator");
        return (integers, integer) -> integers.add(multiplier * integer);
    }

    @Override
    public BinaryOperator<List<Integer>> combiner() {
        print("In combiner");
        return new BinaryOperator<List<Integer>>() {
            @Override
            public List<Integer> apply(List<Integer> integers, List<Integer> integers2) {
                integers.addAll(integers2);
                return integers;
            }
        };
    }

    @Override
    public Function<List<Integer>, List<Integer>> finisher() {
        print("In finisher");
        return integers -> integers;
    }

    @Override
    public Set<Characteristics> characteristics() {
        HashSet<Characteristics> characteristics = new HashSet<>();
        characteristics.add(Characteristics.IDENTITY_FINISH);
        return characteristics;
    }
}
