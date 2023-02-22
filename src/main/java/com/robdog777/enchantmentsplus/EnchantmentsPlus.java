package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.enchants.*;
import com.robdog777.enchantmentsplus.statuseffects.MoonRestEffect;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantmentsPlus implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    public static final Logger LOGGER = LoggerFactory.getLogger("enchantmentsplus");

    // Audio files copyright free from https://freesound.org/
    public static final Identifier SWOOP = new Identifier("enchantmentsplus:swoop");
    public static final Identifier BLURP = new Identifier("enchantmentsplus:blurp");
    // moon png file from https://www.pngitem.com
    public static final StatusEffect MOONREST = new MoonRestEffect();
    public static final Enchantment FROSTBITE = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "frostbite"),
            new FrostbiteEnchantment()
    );
    public static final Enchantment LUNARSIGHT = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "lunarsight"),
            new LunarSightEnchantment()
    );
    public static final Enchantment CUBICAL = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "cubical"),
            new CubicalEnchantment()
    );
    public static final Enchantment ENDSLAYER = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "endslayer"),
            new EndSlayerEnchantment()
    );
    public static final Enchantment PAYBACK = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "payback"),
            new PaybackEnchantment()
    );
    public static final Enchantment LIFESTEAL = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "lifesteal"),
            new LifeStealEnchantment()
    );
    public static final Enchantment LEVITATION = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "levitation"),
            new LevitationEnchantment()
    );
    public static final Enchantment BLAZEWALKER = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "blazewalker"),
            new BlazeWalkerEnchantment()
    );
    public static final Enchantment FLASHFORGE = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "flashforge"),
            new FlashForgeEnchantment()
    );
    public static final Enchantment MOONWALKER = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "moonwalker"),
            new MoonWalkerEnchantment()
    );
    public static final Enchantment RAIDER = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "raider"),
            new RaiderEnchantment()
    );
    public static final Enchantment TOXICSTRIKE = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "toxicstrike"),
            new ToxicStrikeEnchantment()
    );
    public static final Enchantment HIKER = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "hiker"),
            new HikerEnchantment()
    );
    public static final Enchantment DUALLEAP = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "dualleap"),
            new DualLeapEnchantment()
    );
    private static final Enchantment THUNDERLORD = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "thunderlord"),
            new ThunderlordEnchantment()
    );
    public static SoundEvent SwoopEvent = new SoundEvent(SWOOP);
    public static SoundEvent BlurpEvent = new SoundEvent(BLURP);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("enchantmentsplus is now loaded");

        Registry.register(Registry.STATUS_EFFECT, new Identifier("enchantmentsplus", "moonresteffect"), MOONREST);
        Registry.register(Registry.SOUND_EVENT, EnchantmentsPlus.SWOOP, SwoopEvent);
        Registry.register(Registry.SOUND_EVENT, EnchantmentsPlus.BLURP, BlurpEvent);
    }
}
