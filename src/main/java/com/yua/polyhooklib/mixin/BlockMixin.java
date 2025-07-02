package com.yua.polyhooklib.mixin;

import com.yua.polyhooklib.api.IBlockAccess;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin implements IBlockAccess {
    @Override
    public Block getAsBlock() {
        return (Block) (Object) this;
    }
}
