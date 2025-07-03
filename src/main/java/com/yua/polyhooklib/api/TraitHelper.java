package com.yua.polyhooklib.api;

import com.yua.polyhooklib.trait.*;

import java.util.Optional;

@SuppressWarnings("unused")
public class TraitHelper {
    public static <T, R> Optional<TraitFunction<T, R>> getFunction(TraitID traitId, Class<?> targetClass) {
        return Optional.ofNullable(TraitFunctionRegistry.get(traitId, targetClass));
    }

    public static <T> Optional<Trait<T>> getTrait(TraitID traitId, Class<?> targetClass) {
        return Optional.ofNullable(TraitRegistry.get(traitId, targetClass));
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Optional<R> applyFunction(TraitID traitId, Class<?> targetClass, T input) {
        return getFunction(traitId, targetClass).map(func ->(R)func.apply(input));
    }

    public static <T> boolean tryApplyTrait(TraitID traitId, Class<?> targetClass, T input) {
        return getTrait(traitId, targetClass).map(trait -> {
            trait.apply(input);
            return true;
        }).orElse(false);
    }

}