package com.yua.polyhooklib.trait;

@FunctionalInterface
public interface Trait<T> {
    void apply(T target);
}
