package com.subhrajit.roy.usagetracker;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@RequiresApi(api = Build.VERSION_CODES.N)
public class UsageCollector implements Collector<Usage,Long,Long> {
    @Override
    public Supplier<Long> supplier() {
        return null;
    }

    @Override
    public BiConsumer<Long, Usage> accumulator() {
        return null;
    }

    @Override
    public BinaryOperator<Long> combiner() {
        return null;
    }

    @Override
    public Function<Long, Long> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return null;
    }
}
