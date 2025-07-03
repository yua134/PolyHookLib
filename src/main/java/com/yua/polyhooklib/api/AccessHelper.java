package com.yua.polyhooklib.api;

import com.yua.polyhooklib.access.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

@SuppressWarnings("unused")
public class AccessHelper {
    public static Optional<Block> tryAsBlock(Object obj) {
        if (obj instanceof IBlockAccess access) {
            return Optional.ofNullable(access.getAsBlock());
        }
        return Optional.empty();
    }

    public static Optional<BlockEntity> tryAsBlockEntity(Object obj) {
        if (obj instanceof IBlockEntityAccess access) {
            return Optional.ofNullable(access.getAsBlockEntity());
        }
        return Optional.empty();
    }

    public static Optional<Player> tryAsPlayer(Object obj) {
        if (obj instanceof IPlayerAccess access) {
            return Optional.ofNullable(access.getAsPlayer());
        }
        return Optional.empty();
    }

    public static Optional<Entity> tryAsEntity(Object obj) {
        if (obj instanceof IEntityAccess access) {
            return Optional.ofNullable(access.getAsEntity());
        }
        return Optional.empty();
    }

    public static Optional<Item> tryAsItem(Object obj) {
        if (obj instanceof IItemAccess access) {
            return Optional.ofNullable(access.getAsItem());
        }
        return Optional.empty();
    }

    public static Optional<Level> tryAsLevel(Object obj) {
        if (obj instanceof ILevelAccess access) {
            return Optional.ofNullable(access.getAsLevel());
        }
        return Optional.empty();
    }
}
