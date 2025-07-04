package com.yua.polyhooklib.trait;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "polyhooklib", bus = Mod.EventBusSubscriber.Bus.MOD)
public class TraitFunctionRegistry {
    private static final Map<TraitID, Map<Class<?>, TraitFunction<?, ?>>> registry = new HashMap<>();
    private static boolean locked = false;
    private static final Logger LOGGER = LogManager.getLogger();

    public static <T, R> void register(String Id, Class<T> targetClass, TraitFunction<T, R> impl) {
        if (locked) {
            throw new IllegalStateException("Trait registration is locked after load complete");
        }

        LOGGER.info("function register worked");

        TraitID traitId = TraitID.of(Id);

        Map<Class<?>, TraitFunction<?,?>> innerMap = registry.computeIfAbsent(traitId, k -> new HashMap<>());

        if (innerMap.containsKey(targetClass)) {
            LOGGER.warn("[PolyHookLib] WARNING: Duplicate registration for TraitID {} on {}", traitId, targetClass.getName());
            return;
        }

        innerMap.put(targetClass, impl);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> TraitFunction<T, R> get(
            TraitID traitId, Class<?> targetClass
    ) {
        return (TraitFunction<T, R>) registry
                .getOrDefault(traitId, Map.of())
                .getOrDefault(targetClass, t -> null);
    }

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
        LOGGER.info("[PolyHookLib]: function register locked");
        locked = true;
    }
}
