package com.yua.polyhooklib.api;

import com.yua.polyhooklib.trait.*;

import java.util.Optional;

@SuppressWarnings("unused")
public class TraitHelper {
    public static <T, R> Optional<TraitFunction<T, R>> getFunction(String fullId, Class<?> targetClass) {
        return Optional.ofNullable(TraitFunctionRegistry.get(TraitID.from(fullId), targetClass));
    }

    public static <T> Optional<Trait<T>> getTrait(String fullId, Class<?> targetClass) {
        return Optional.ofNullable(TraitRegistry.get(TraitID.from(fullId), targetClass));
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Optional<R> trtApplyFunction(String fullId, Class<?> targetClass, T input) {
        return getFunction(fullId, targetClass).map(func ->(R)func.apply(input));
    }

    public static <T> boolean tryApplyTrait(String fullId, Class<?> targetClass, T input) {
        return getTrait(fullId, targetClass).map(trait -> {
            trait.apply(input);
            return true;
        }).orElse(false);
    }

}