package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.config.EnchantmentsPlusConfig;
import com.robdog777.enchantmentsplus.enchants.*;
import com.robdog777.enchantmentsplus.statuseffects.MoonRestEffect;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantmentsPlus implements ModInitializer {
    // This logger is used to write text to the console and the log file
    public static final Logger LOGGER = LoggerFactory.getLogger("enchantmentsplus");

    // moon png file from https://www.pngitem.com
    // Audio files copyright free from https://freesound.org/
    public static final Identifier SWOOP = new Identifier("enchantmentsplus:swoop");
    public static final Identifier BLURP = new Identifier("enchantmentsplus:blurp");

    private static final ConfigHolder<EnchantmentsPlusConfig> CONFIG_HOLDER = AutoConfig.register(EnchantmentsPlusConfig.class, JanksonConfigSerializer::new);

    public static StatusEffect MOONREST = new MoonRestEffect();

    public static Enchantment BLAZEWALKER = null, CUBICAL = null, DUALLEAP = null, ENDSLAYER = null, FLASHFORGE = null,
            FROSTBITE = null, HIKER = null, LEVITATION = null, LIFESTEAL = null, LUNARSIGHT = null, MOONWALKER = null,
            PAYBACK = null, RAIDER = null, THUNDERLORD = null, TOXICSTRIKE = null, SNIPER = null;

    public static SoundEvent SwoopEvent = SoundEvent.of(SWOOP);
    public static SoundEvent BlurpEvent = SoundEvent.of(BLURP);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.

        LOGGER.info("enchantmentsplus is now loaded");

        Registry.register(Registries.STATUS_EFFECT, new Identifier("enchantmentsplus", "moonresteffect"), MOONREST);
        Registry.register(Registries.SOUND_EVENT, EnchantmentsPlus.SWOOP, SwoopEvent);
        Registry.register(Registries.SOUND_EVENT, EnchantmentsPlus.BLURP, BlurpEvent);

        EnchantmentsPlusConfig config = AutoConfig.getConfigHolder(EnchantmentsPlusConfig.class).getConfig();
        CONFIG_HOLDER.load();

        if (CONFIG_HOLDER.getConfig().enableBlazeWalker) {
            BLAZEWALKER = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "blazewalker"),
                    new BlazeWalkerEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableCubical) {
            CUBICAL = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "cubical"),
                    new CubicalEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableDualLeap) {
            DUALLEAP = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "dualleap"),
                    new DualLeapEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableEndSlayer) {
            ENDSLAYER = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "endslayer"),
                    new EndSlayerEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableFlashForge) {
            FLASHFORGE = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "flashforge"),
                    new FlashForgeEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableFrostbite) {
            FROSTBITE = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "frostbite"),
                    new FrostbiteEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableHiker) {
            HIKER = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "hiker"),
                    new HikerEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableLevitation) {
            LEVITATION = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "levitation"),
                    new LevitationEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableLifeSteal) {
            LIFESTEAL = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "lifesteal"),
                    new LifeStealEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableLunarSight) {
            LUNARSIGHT = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "lunarsight"),
                    new LunarSightEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableMoonWalker) {
            MOONWALKER = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "moonwalker"),
                    new MoonWalkerEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enablePayback) {
            PAYBACK = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "payback"),
                    new PaybackEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableRaider) {
            RAIDER = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "raider"),
                    new RaiderEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableThunderlord) {
            THUNDERLORD = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "thunderlord"),
                    new ThunderlordEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableToxicStrike) {
            TOXICSTRIKE = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "toxicstrike"),
                    new ToxicStrikeEnchantment()
            );
        }
        if (CONFIG_HOLDER.getConfig().enableSniper) {
            SNIPER = Registry.register(
                    Registries.ENCHANTMENT,
                    new Identifier("enchantmentsplus", "sniper"),
                    new SniperEnchantment()
            );
        }
    }
}
