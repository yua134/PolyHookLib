package com.yua.polyhooklib;


import com.mojang.brigadier.CommandDispatcher;
import com.yua.polyhooklib.access.*;
import com.yua.polyhooklib.trait.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("polyhooklib")
public class PolyHookLib {
    public PolyHookLib() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("phl_test")
                .requires(cs -> cs.hasPermission(2)) // OPレベル2以上
                .executes(ctx -> {
                    CommandSourceStack src = ctx.getSource();
                    ServerPlayer player = src.getPlayer();
                    ServerLevel level = (ServerLevel) player.level();

                    src.sendSuccess(() -> debugAll(player, level), true);
                    return 1;
                }));
    }

    private net.minecraft.network.chat.Component debugAll(ServerPlayer player, ServerLevel level) {
        System.out.println("[PHL] ---- Running Mixin Debug Test ----");

        Block block = Blocks.STONE;
        System.out.println("[PHL] BlockMixin: " + (block instanceof IBlockAccess));

        BlockEntity be = new ChestBlockEntity(BlockPos.ZERO, Blocks.CHEST.defaultBlockState());
        System.out.println("[PHL] BlockEntityMixin: " + (be instanceof IBlockEntityAccess));

        Entity entity = new ItemEntity(level, 0, 0, 0, new ItemStack(Items.DIAMOND));
        System.out.println("[PHL] EntityMixin: " + (entity instanceof IEntityAccess));

        Item item = Items.GOLD_INGOT;
        System.out.println("[PHL] ItemMixin: " + (item instanceof IItemAccess));

        System.out.println("[PHL] LevelMixin: " + (level instanceof ILevelAccess));

        PlayerMixinTest(player);

        System.out.println("[PHL] ---- Debug Complete ----");

        return net.minecraft.network.chat.Component.literal("Mixin debug result logged to console.");
    }

    private void PlayerMixinTest(ServerPlayer player) {
        boolean result = player instanceof IPlayerAccess;
        System.out.println("[PHL] PlayerMixin: " + result);
    }
}
