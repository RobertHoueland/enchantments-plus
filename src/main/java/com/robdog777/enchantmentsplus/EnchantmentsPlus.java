package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.config.EnchantmentsPlusConfig;
import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import com.robdog777.enchantmentsplus.statuseffects.MoonRestEffect;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantmentsPlus implements ModInitializer {
    public static final String MOD_ID = "enchantmentsplus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // moon png file from https://www.pngitem.com
    // Audio files copyright free from https://freesound.org/
    public static final Identifier SWOOP = Identifier.of(MOD_ID, "swoop");
    public static final Identifier BLURP = Identifier.of(MOD_ID, "blurp");
    public static final Identifier WHOOSH = Identifier.of(MOD_ID, "whoosh");
    public static final Identifier DENY = Identifier.of(MOD_ID, "deny");

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

    public static final ConfigHolder<EnchantmentsPlusConfig> CONFIG_HOLDER = AutoConfig.register(
            EnchantmentsPlusConfig.class, JanksonConfigSerializer::new);

    public static final StatusEffect MOONREST = new MoonRestEffect();

    public static final SoundEvent SwoopEvent = SoundEvent.of(SWOOP);
    public static final SoundEvent BlurpEvent = SoundEvent.of(BLURP);
    public static final SoundEvent WhooshEvent = SoundEvent.of(WHOOSH);
    public static final SoundEvent DenyEvent = SoundEvent.of(DENY);

    public static boolean isEnchantmentEnabled(RegistryEntry<Enchantment> entry) {
        if (entry.matchesKey(BLAZEWALKER)) return CONFIG_HOLDER.getConfig().enableBlazeWalker;
        if (entry.matchesKey(CUBICAL)) return CONFIG_HOLDER.getConfig().enableCubical;
        if (entry.matchesKey(DUALLEAP)) return CONFIG_HOLDER.getConfig().enableDualLeap;
        if (entry.matchesKey(ENDSLAYER)) return CONFIG_HOLDER.getConfig().enableEndSlayer;
        if (entry.matchesKey(EXCAVATOR)) return CONFIG_HOLDER.getConfig().enableExcavator;
        if (entry.matchesKey(FLASHFORGE)) return CONFIG_HOLDER.getConfig().enableFlashForge;
        if (entry.matchesKey(FROSTBITE)) return CONFIG_HOLDER.getConfig().enableFrostbite;
        if (entry.matchesKey(HIKER)) return CONFIG_HOLDER.getConfig().enableHiker;
        if (entry.matchesKey(LEVITATION)) return CONFIG_HOLDER.getConfig().enableLevitation;
        if (entry.matchesKey(LIFESTEAL)) return CONFIG_HOLDER.getConfig().enableLifeSteal;
        if (entry.matchesKey(LUNARSIGHT)) return CONFIG_HOLDER.getConfig().enableLunarSight;
        if (entry.matchesKey(MOONWALKER)) return CONFIG_HOLDER.getConfig().enableMoonWalker;
        if (entry.matchesKey(MYSTICMIND)) return CONFIG_HOLDER.getConfig().enableMysticMind;
        if (entry.matchesKey(PAYBACK)) return CONFIG_HOLDER.getConfig().enablePayback;
        if (entry.matchesKey(RAIDER)) return CONFIG_HOLDER.getConfig().enableRaider;
        if (entry.matchesKey(SNIPER)) return CONFIG_HOLDER.getConfig().enableSniper;
        if (entry.matchesKey(STORMSTRIKE)) return CONFIG_HOLDER.getConfig().enableStormStrike;
        if (entry.matchesKey(THUNDERLORD)) return CONFIG_HOLDER.getConfig().enableThunderlord;
        if (entry.matchesKey(TOXICSTRIKE)) return CONFIG_HOLDER.getConfig().enableToxicStrike;
        return true;
    }

    private static RegistryKey<Enchantment> enchantmentKey(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(MOD_ID, id));
    }

    @Override
    public void onInitialize() {
        LOGGER.info("enchantmentsplus is now loaded");

        CONFIG_HOLDER.load();

        Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "moonresteffect"), MOONREST);
        Registry.register(Registries.SOUND_EVENT, SWOOP, SwoopEvent);
        Registry.register(Registries.SOUND_EVENT, BLURP, BlurpEvent);
        Registry.register(Registries.SOUND_EVENT, WHOOSH, WhooshEvent);
        Registry.register(Registries.SOUND_EVENT, DENY, DenyEvent);
        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(MOD_ID, "custom"), EnchantmentEffects.CODEC);
    }
}
