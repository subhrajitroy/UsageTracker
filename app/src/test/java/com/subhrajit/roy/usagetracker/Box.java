package com.subhrajit.roy.usagetracker;

public class Box {

    private Long value;

    public Box(Long t){
        this.value = t;
    }

    public void add(Long t){
        value += t;
    }

    public Box combine(Box other){
        return new Box(this.value + other.value);
    }

    public Long unbox() {
        return value;
    }
}
