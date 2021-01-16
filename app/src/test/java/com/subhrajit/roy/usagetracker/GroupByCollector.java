package com.subhrajit.roy.usagetracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class  GroupByCollector<T,K> implements Collector<T, Map<K, List<T>>,Map<K, List<T>>> {

    private Function<T, K> func;

    public GroupByCollector(Function<T,K> func){

        this.func = func;
    }

    @Override
    public Supplier<Map<K, List<T>>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<K , List<T>>, T> accumulator() {

        return (objectTMap, t) -> {
            K key = func.apply(t);
            if(objectTMap.containsKey(key)){
                List<T> items = objectTMap.get(key);
                items.add(t);
            }else{
                List<T> items = new ArrayList<>();
                items.add(t);
                objectTMap.put(key,items);
            }
        };
    }

    @Override
    public BinaryOperator<Map<K, List<T>>> combiner() {
        return (objectListMap, objectListMap2) -> {
            HashMap<K, List<T>> combined = new HashMap<>(objectListMap);
            objectListMap2.keySet().forEach(k -> {
                List<T> value = objectListMap2.get(k);
                combined.merge(k, value,(v1,v2) -> {
                    v1.addAll(v2);
                    return v1;
                });
            });
            return combined;
        };
    }

    @Override
    public Function<Map<K, List<T>>, Map<K, List<T>>> finisher() {
        return objectListMap -> objectListMap;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return CollectionCharacteristics.CHARACTERISTICS;
    }
}
