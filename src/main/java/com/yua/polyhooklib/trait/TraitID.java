package com.yua.polyhooklib.trait;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = "polyhooklib", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class TraitID {
    private final String id;
    private static boolean locked = false;
    private static final Logger LOGGER = LogManager.getLogger();

    private TraitID(String id) {
        this.id = id;
    }

    public static TraitID from(String fullId) {
        return new TraitID(fullId);
    }

    static TraitID of(String localName) {
        if (locked) {
            throw new IllegalStateException("Trait registration is locked after load complete");
        }

        String modid;
        try {
            modid = ModLoadingContext.get().getActiveContainer().getModId();
        } catch (Exception e) {
            throw new IllegalStateException("TraitID.of(...) must be called during mod loading (before LoadCompleteEvent).", e);
        }

        return new TraitID(modid + ":" + localName);
    }

    public String id() {
        return id;
    }

    @Override public boolean equals(Object o) { return o instanceof TraitID tid && tid.id.equals(this.id); }
    @Override public int hashCode() { return id.hashCode(); }
    @Override public String toString() { return "TraitID(" + id + ")"; }

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
        LOGGER.info("[PolyHookLib]: TraitID.of locked");
        locked = true;
    }
}