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
    public static final Logger LOGGER = LoggerFactory.getLogger("enchantmentsplus");

    // moon png file from https://www.pngitem.com
    // Audio files copyright free from https://freesound.org/
    public static final Identifier SWOOP = new Identifier("enchantmentsplus:swoop");
    public static final Identifier BLURP = new Identifier("enchantmentsplus:blurp");
    public static final Identifier WHOOSH = new Identifier("enchantmentsplus:whoosh");
    public static final Identifier DENY = new Identifier("enchantmentsplus:deny");

    public static final ConfigHolder<EnchantmentsPlusConfig> CONFIG_HOLDER = AutoConfig.register(
            EnchantmentsPlusConfig.class, JanksonConfigSerializer::new);

    public static StatusEffect MOONREST = new MoonRestEffect();

    public static Enchantment BLAZEWALKER = null, CUBICAL = null, DUALLEAP = null, ENDSLAYER = null, EXCAVATOR = null,
            FLASHFORGE = null, FROSTBITE = null, HIKER = null, LEVITATION = null, LIFESTEAL = null, LUNARSIGHT = null,
            MOONWALKER = null, PAYBACK = null, RAIDER = null, THUNDERLORD = null, TOXICSTRIKE = null, SNIPER = null,
            MYSTICMIND = null, STORMSTRIKE = null;

    public static SoundEvent SwoopEvent = SoundEvent.of(SWOOP);
    public static SoundEvent BlurpEvent = SoundEvent.of(BLURP);
    public static SoundEvent WhooshEvent = SoundEvent.of(WHOOSH);
    public static SoundEvent DenyEvent = SoundEvent.of(DENY);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        LOGGER.info("enchantmentsplus is now loaded");

        Registry.register(Registries.STATUS_EFFECT, new Identifier("enchantmentsplus", "moonresteffect"), MOONREST);
        Registry.register(Registries.SOUND_EVENT, EnchantmentsPlus.SWOOP, SwoopEvent);
        Registry.register(Registries.SOUND_EVENT, EnchantmentsPlus.BLURP, BlurpEvent);
        Registry.register(Registries.SOUND_EVENT, EnchantmentsPlus.WHOOSH, WhooshEvent);
        Registry.register(Registries.SOUND_EVENT, EnchantmentsPlus.DENY, DenyEvent);

        AutoConfig.getConfigHolder(EnchantmentsPlusConfig.class).getConfig();
        CONFIG_HOLDER.load();

        BLAZEWALKER = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "blazewalker"),
                new BlazeWalkerEnchantment()
        );

        CUBICAL = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "cubical"),
                new CubicalEnchantment()
        );

        DUALLEAP = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "dualleap"),
                new DualLeapEnchantment()
        );

        ENDSLAYER = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "endslayer"),
                new EndSlayerEnchantment()
        );

        EXCAVATOR = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "excavator"),
                new ExcavatorEnchantment()
        );

        FLASHFORGE = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "flashforge"),
                new FlashForgeEnchantment()
        );

        FROSTBITE = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "frostbite"),
                new FrostbiteEnchantment()
        );


        HIKER = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "hiker"),
                new HikerEnchantment()
        );


        LEVITATION = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "levitation"),
                new LevitationEnchantment()
        );

        LIFESTEAL = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "lifesteal"),
                new LifeStealEnchantment()
        );


        LUNARSIGHT = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "lunarsight"),
                new LunarSightEnchantment()
        );


        MOONWALKER = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "moonwalker"),
                new MoonWalkerEnchantment()
        );

        MYSTICMIND = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "mysticmind"),
                new MysticMindEnchantment()
        );


        PAYBACK = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "payback"),
                new PaybackEnchantment()
        );

        RAIDER = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "raider"),
                new RaiderEnchantment()
        );

        SNIPER = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "sniper"),
                new SniperEnchantment()
        );

        STORMSTRIKE = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "stormstrike"),
                new StormStrikeEnchantment()
        );

        THUNDERLORD = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "thunderlord"),
                new ThunderlordEnchantment()
        );

        TOXICSTRIKE = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("enchantmentsplus", "toxicstrike"),
                new ToxicStrikeEnchantment()
        );
    }
}
