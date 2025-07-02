package com.yua.polyhooklib.mixin;

import com.yua.polyhooklib.api.IItemAccess;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ItemMixin implements IItemAccess {
    @Override
    public Item getAsItem() {
        return (Item) (Object) this;
    }
}
