package com.yua.polyhooklib.mixin;

import com.yua.polyhooklib.api.IPlayerAccess;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerMixin implements IPlayerAccess {
    @Override
    public Player getAsPlayer() {
        return (Player) (Object) this;
    }
}
