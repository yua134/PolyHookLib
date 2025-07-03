package com.yua.polyhooklib.mixin;

import com.yua.polyhooklib.access.IEntityAccess;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityAccess {
    @Override
    public Entity getAsEntity() {
        return (Entity) (Object) this;
    }
}
