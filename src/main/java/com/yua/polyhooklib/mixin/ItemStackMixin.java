package com.yua.polyhooklib.mixin;

import com.yua.polyhooklib.access.IItemStackAccess;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IItemStackAccess {
}
