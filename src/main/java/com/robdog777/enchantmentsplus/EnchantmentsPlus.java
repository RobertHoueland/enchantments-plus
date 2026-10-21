package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.config.ClothConfigIntegration;
import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import com.robdog777.enchantmentsplus.network.EnchantmentConfigNetworking;
import com.robdog777.enchantmentsplus.statuseffects.MoonRestEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EnchantmentsPlus implements ModInitializer {
    public static final String MOD_ID = "enchantmentsplus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // moon png file from https://www.pngitem.com
    // Audio files copyright free from https://freesound.org/
    public static final Identifier SWOOP = Identifier.of(MOD_ID, "swoop");
    public static final SoundEvent SwoopEvent = SoundEvent.of(SWOOP);
    public static final Identifier BLURP = Identifier.of(MOD_ID, "blurp");
    public static final SoundEvent BlurpEvent = SoundEvent.of(BLURP);
    public static final Identifier WHOOSH = Identifier.of(MOD_ID, "whoosh");
    public static final SoundEvent WhooshEvent = SoundEvent.of(WHOOSH);
    public static final Identifier DENY = Identifier.of(MOD_ID, "deny");
    public static final SoundEvent DenyEvent = SoundEvent.of(DENY);
    public static final RegistryKey<Enchantment> BLAZEWALKER = enchantmentKey("blazewalker");
    public static final RegistryKey<Enchantment> CUBICAL = enchantmentKey("cubical");
    public static final RegistryKey<Enchantment> DUALLEAP = enchantmentKey("dualleap");
    public static final RegistryKey<Enchantment> ENDSLAYER = enchantmentKey("endslayer");
    public static final RegistryKey<Enchantment> EXCAVATOR = enchantmentKey("excavator");
    public static final RegistryKey<Enchantment> FLASHFORGE = enchantmentKey("flashforge");
    public static final RegistryKey<Enchantment> FROSTBITE = enchantmentKey("frostbite");
    public static final RegistryKey<Enchantment> HIKER = enchantmentKey("hiker");
    public static final RegistryKey<Enchantment> LEVITATION = enchantmentKey("levitation");
    public static final RegistryKey<Enchantment> LIFESTEAL = enchantmentKey("lifesteal");
    public static final RegistryKey<Enchantment> LUNARSIGHT = enchantmentKey("lunarsight");
    public static final RegistryKey<Enchantment> MOONWALKER = enchantmentKey("moonwalker");
    public static final RegistryKey<Enchantment> MYSTICMIND = enchantmentKey("mysticmind");
    public static final RegistryKey<Enchantment> PAYBACK = enchantmentKey("payback");
    public static final RegistryKey<Enchantment> RAIDER = enchantmentKey("raider");
    public static final RegistryKey<Enchantment> SNIPER = enchantmentKey("sniper");
    public static final RegistryKey<Enchantment> STORMSTRIKE = enchantmentKey("stormstrike");
    public static final RegistryKey<Enchantment> THUNDERLORD = enchantmentKey("thunderlord");
    public static final RegistryKey<Enchantment> TOXICSTRIKE = enchantmentKey("toxicstrike");
    // order matters here, add new enchantments to end
    private static final List<RegistryKey<Enchantment>> ENCHANTMENT_KEYS = List.of(
            BLAZEWALKER, CUBICAL, DUALLEAP, ENDSLAYER, EXCAVATOR, FLASHFORGE, FROSTBITE, HIKER, LEVITATION,
            LIFESTEAL, LUNARSIGHT, MOONWALKER, MYSTICMIND, PAYBACK, RAIDER, SNIPER, STORMSTRIKE, THUNDERLORD,
            TOXICSTRIKE);
    private static final boolean CLOTH_CONFIG_LOADED = FabricLoader.getInstance().isModLoaded("cloth-config");
    public static final StatusEffect MOONREST = new MoonRestEffect();
    private static volatile Long syncedEnabledEnchantmentMask;

    public static boolean isEnchantmentEnabled(RegistryEntry<Enchantment> entry) {
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

    public static boolean isEnchantmentEnabled(RegistryKey<Enchantment> key, World world) {
        int index = ENCHANTMENT_KEYS.indexOf(key);
        if (index < 0) {
            return true;
        }

        Long serverMask = syncedEnabledEnchantmentMask;
        if (world.isClient() && serverMask != null) {
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

    private static int getEnchantmentIndex(RegistryEntry<Enchantment> entry) {
        for (int i = 0; i < ENCHANTMENT_KEYS.size(); i++) {
            if (entry.matchesKey(ENCHANTMENT_KEYS.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isEnchantmentEnabled(int index) {
        return !CLOTH_CONFIG_LOADED || ClothConfigIntegration.isEnchantmentEnabled(index);
    }

    public static int getConfiguredMaxLevel(RegistryKey<?> key) {
        if (!CLOTH_CONFIG_LOADED || !key.isOf(RegistryKeys.ENCHANTMENT)
                || !key.getValue().getNamespace().equals(MOD_ID)) {
            return -1;
        }
        return ClothConfigIntegration.getMaxLevel(key.getValue().getPath());
    }

    private static RegistryKey<Enchantment> enchantmentKey(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(MOD_ID, id));
    }

    @Override
    public void onInitialize() {
        LOGGER.info("enchantmentsplus is now loaded");

        if (CLOTH_CONFIG_LOADED) {
            ClothConfigIntegration.load();
        }
        EnchantmentConfigNetworking.initialize();

        Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "moonresteffect"), MOONREST);
        Registry.register(Registries.SOUND_EVENT, SWOOP, SwoopEvent);
        Registry.register(Registries.SOUND_EVENT, BLURP, BlurpEvent);
        Registry.register(Registries.SOUND_EVENT, WHOOSH, WhooshEvent);
        Registry.register(Registries.SOUND_EVENT, DENY, DenyEvent);
        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MOD_ID, "custom"), EnchantmentEffects.CODEC);
    }
}
