package com.subhrajit.roy.usagetracker;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CountCollector implements Collector<Long,Box,Long> {
    @Override
    public Supplier<Box> supplier() {
        return () -> new Box(0L);
    }


    @Override
    public BiConsumer<Box, Long> accumulator() {
        return Box::add;
    }

    @Override
    public BinaryOperator<Box> combiner() {
        return Box::combine;
    }

    @Override
    public Function<Box, Long> finisher() {
        return Box::unbox;
    }


    @Override
    public Set<Characteristics> characteristics() {
        return CollectionCharacteristics.CHARACTERISTICS;
    }
}
