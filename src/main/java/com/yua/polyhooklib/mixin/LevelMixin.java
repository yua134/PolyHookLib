package com.yua.polyhooklib.mixin;

import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import com.yua.polyhooklib.access.ILevelAccess;

@Mixin(Level.class)
public abstract class LevelMixin implements ILevelAccess {
    @Override
    public Level getAsLevel() {
        return (Level) (Object) this;
    }
}
