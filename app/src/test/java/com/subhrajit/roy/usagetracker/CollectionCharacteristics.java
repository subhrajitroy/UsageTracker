package com.subhrajit.roy.usagetracker;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;

public class CollectionCharacteristics {

    public static Set<Collector.Characteristics> CHARACTERISTICS;

    static {
        CHARACTERISTICS = new HashSet<>();
        CHARACTERISTICS.add(Collector.Characteristics.CONCURRENT);
    }
}
