package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.config.ClothConfigIntegration;
import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import com.robdog777.enchantmentsplus.network.EnchantmentConfigNetworking;
import com.robdog777.enchantmentsplus.statuseffects.MoonRestEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EnchantmentsPlus implements ModInitializer {
    public static final String MOD_ID = "enchantmentsplus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // moon png file from https://www.pngitem.com
    // Audio files copyright free from https://freesound.org/
    public static final Identifier SWOOP = Identifier.fromNamespaceAndPath(MOD_ID, "swoop");
    public static final SoundEvent SwoopEvent = SoundEvent.createVariableRangeEvent(SWOOP);
    public static final Identifier BLURP = Identifier.fromNamespaceAndPath(MOD_ID, "blurp");
    public static final SoundEvent BlurpEvent = SoundEvent.createVariableRangeEvent(BLURP);
    public static final Identifier WHOOSH = Identifier.fromNamespaceAndPath(MOD_ID, "whoosh");
    public static final SoundEvent WhooshEvent = SoundEvent.createVariableRangeEvent(WHOOSH);
    public static final Identifier DENY = Identifier.fromNamespaceAndPath(MOD_ID, "deny");
    public static final SoundEvent DenyEvent = SoundEvent.createVariableRangeEvent(DENY);
    public static final ResourceKey<Enchantment> BLAZEWALKER = enchantmentKey("blazewalker");
    public static final ResourceKey<Enchantment> CUBICAL = enchantmentKey("cubical");
    public static final ResourceKey<Enchantment> DUALLEAP = enchantmentKey("dualleap");
    public static final ResourceKey<Enchantment> ENDSLAYER = enchantmentKey("endslayer");
    public static final ResourceKey<Enchantment> EXCAVATOR = enchantmentKey("excavator");
    public static final ResourceKey<Enchantment> FLASHFORGE = enchantmentKey("flashforge");
    public static final ResourceKey<Enchantment> FROSTBITE = enchantmentKey("frostbite");
    public static final ResourceKey<Enchantment> HIKER = enchantmentKey("hiker");
    public static final ResourceKey<Enchantment> LEVITATION = enchantmentKey("levitation");
    public static final ResourceKey<Enchantment> LIFESTEAL = enchantmentKey("lifesteal");
    public static final ResourceKey<Enchantment> LUNARSIGHT = enchantmentKey("lunarsight");
    public static final ResourceKey<Enchantment> MOONWALKER = enchantmentKey("moonwalker");
    public static final ResourceKey<Enchantment> MYSTICMIND = enchantmentKey("mysticmind");
    public static final ResourceKey<Enchantment> PAYBACK = enchantmentKey("payback");
    public static final ResourceKey<Enchantment> RAIDER = enchantmentKey("raider");
    public static final ResourceKey<Enchantment> SNIPER = enchantmentKey("sniper");
    public static final ResourceKey<Enchantment> STORMSTRIKE = enchantmentKey("stormstrike");
    public static final ResourceKey<Enchantment> THUNDERLORD = enchantmentKey("thunderlord");
    public static final ResourceKey<Enchantment> TOXICSTRIKE = enchantmentKey("toxicstrike");
    // order matters here, add new enchantments to end
    private static final List<ResourceKey<Enchantment>> ENCHANTMENT_KEYS = List.of(
            BLAZEWALKER, CUBICAL, DUALLEAP, ENDSLAYER, EXCAVATOR, FLASHFORGE, FROSTBITE, HIKER, LEVITATION,
            LIFESTEAL, LUNARSIGHT, MOONWALKER, MYSTICMIND, PAYBACK, RAIDER, SNIPER, STORMSTRIKE, THUNDERLORD,
            TOXICSTRIKE);
    private static final boolean CLOTH_CONFIG_LOADED = FabricLoader.getInstance().isModLoaded("cloth-config");
    public static final MobEffect MOONREST = new MoonRestEffect();
    private static volatile Long syncedEnabledEnchantmentMask;

    public static boolean isEnchantmentEnabled(Holder<Enchantment> entry) {
        int index = getEnchantmentIndex(entry);
        if (index < 0) {
            return true;
        }

        Long serverMask = syncedEnabledEnchantmentMask;
        if (serverMask != null) {
            return (serverMask & (1L << index)) != 0;
        }

        return isEnchantmentEnabled(index);
    }

    public static boolean isEnchantmentEnabled(ResourceKey<Enchantment> key, Level world) {
        int index = ENCHANTMENT_KEYS.indexOf(key);
        if (index < 0) {
            return true;
        }

        Long serverMask = syncedEnabledEnchantmentMask;
        if (world.isClientSide() && serverMask != null) {
            return (serverMask & (1L << index)) != 0;
        }

        return isEnchantmentEnabled(index);
    }

    public static long getEnabledEnchantmentMask() {
        long mask = 0L;
        for (int i = 0; i < ENCHANTMENT_KEYS.size(); i++) {
            if (isEnchantmentEnabled(i)) {
                mask |= 1L << i;
            }
        }
        return mask;
    }

    public static void setSyncedEnabledEnchantmentMask(long mask) {
        syncedEnabledEnchantmentMask = mask;
    }

    public static void clearSyncedEnabledEnchantmentMask() {
        syncedEnabledEnchantmentMask = null;
    }

    private static int getEnchantmentIndex(Holder<Enchantment> entry) {
        for (int i = 0; i < ENCHANTMENT_KEYS.size(); i++) {
            if (entry.is(ENCHANTMENT_KEYS.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isEnchantmentEnabled(int index) {
        return !CLOTH_CONFIG_LOADED || ClothConfigIntegration.isEnchantmentEnabled(index);
    }

    public static int getConfiguredMaxLevel(ResourceKey<?> key) {
        if (!CLOTH_CONFIG_LOADED || !key.isFor(Registries.ENCHANTMENT)
                || !key.identifier().getNamespace().equals(MOD_ID)) {
            return -1;
        }
        return ClothConfigIntegration.getMaxLevel(key.identifier().getPath());
    }

    private static ResourceKey<Enchantment> enchantmentKey(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(MOD_ID, id));
    }

    @Override
    public void onInitialize() {
        LOGGER.info("enchantmentsplus is now loaded");

        if (CLOTH_CONFIG_LOADED) {
            ClothConfigIntegration.load();
        }
        EnchantmentConfigNetworking.initialize();

        Registry.register(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(MOD_ID, "moonresteffect"), MOONREST);
        Registry.register(BuiltInRegistries.SOUND_EVENT, SWOOP, SwoopEvent);
        Registry.register(BuiltInRegistries.SOUND_EVENT, BLURP, BlurpEvent);
        Registry.register(BuiltInRegistries.SOUND_EVENT, WHOOSH, WhooshEvent);
        Registry.register(BuiltInRegistries.SOUND_EVENT, DENY, DenyEvent);
        Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "custom"), EnchantmentEffects.CODEC);
    }
}
