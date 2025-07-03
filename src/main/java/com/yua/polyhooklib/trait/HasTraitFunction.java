package com.yua.polyhooklib.trait;

public interface HasTraitFunction<T, R> {
    TraitID getTraitKey();

    default TraitFunction<T, R> getTraitFunction() {
        return TraitFunctionRegistry.get(getTraitKey(), this.getClass());
    }

    @SuppressWarnings("unchecked")
    default R runTraitFunction() {
        return getTraitFunction().apply((T) this);
    }
}

