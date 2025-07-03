package com.yua.polyhooklib.trait;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "polyhooklib", bus = Mod.EventBusSubscriber.Bus.MOD)
public class TraitRegistry {
    private static final Map<TraitID, Map<Class<?>, Trait<?>>> registry = new HashMap<>();
    private static boolean locked = false;
    private static final Logger LOGGER = LogManager.getLogger();

    public static <T> void register(
            TraitID traitId, Class<T> targetClass, Trait<T> impl
    ) {
        if (locked) {
            throw new IllegalStateException("Trait registration is locked after load complete");
        }

        LOGGER.info("trait register worked");

        Map<Class<?>, Trait<?>> innerMap = registry.computeIfAbsent(traitId, k -> new HashMap<>());

        if (innerMap.containsKey(targetClass)) {
            LOGGER.warn("[PolyHookLib] WARNING: Duplicate registration for TraitID {} on {}", traitId, targetClass.getName());
        }

        innerMap.put(targetClass, impl);
    }


    @SuppressWarnings("unchecked")
    public static <T> Trait<T> get(
            TraitID traitId, Class<?> targetClass
    ) {
        return (Trait<T>) registry
                .getOrDefault(traitId, Map.of())
                .getOrDefault(targetClass, null);
    }

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
        LOGGER.info("[PolyHookLib]: trait register locked");
        locked = true;
    }
}