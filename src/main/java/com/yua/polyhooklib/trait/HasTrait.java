package com.yua.polyhooklib.trait;

public interface HasTrait<T> {
    TraitID getTraitKey();

    default Trait<T> getTrait() {
        return TraitRegistry.get(getTraitKey(), this.getClass());
    }

    @SuppressWarnings("unchecked")
    default void runTrait() {
        getTrait().apply((T) this);
    }
}
