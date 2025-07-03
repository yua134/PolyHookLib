package com.yua.polyhooklib.mixin;

import com.yua.polyhooklib.access.IBlockEntityAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements IBlockEntityAccess {
    @Override
    public BlockEntity getAsBlockEntity(){
        return (BlockEntity) (Object) this;
    }
}
