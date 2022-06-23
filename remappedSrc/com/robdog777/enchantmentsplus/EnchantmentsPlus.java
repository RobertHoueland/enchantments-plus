package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.enchants.*;
import com.robdog777.enchantmentsplus.statuseffects.MoonRestEffect;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnchantmentsPlus implements ModInitializer {
    // Audio files copyright free from https://freesound.org/
    public static final Identifier SWOOP = new Identifier("enchantmentsplus:swoop");
    public static SoundEvent SwoopEvent = new SoundEvent(SWOOP);

    public static final Identifier BLURP = new Identifier("enchantmentsplus:blurp");
    public static SoundEvent BlurpEvent = new SoundEvent(BLURP);

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

    private static final Enchantment THUNDERLORD = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "thunderlord"),
            new ThunderlordEnchantment()
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

    @Override
    public void onInitialize() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("enchantmentsplus", "moonresteffect"), MOONREST);
        Registry.register(Registry.SOUND_EVENT, EnchantmentsPlus.SWOOP, SwoopEvent);
        Registry.register(Registry.SOUND_EVENT, EnchantmentsPlus.BLURP, BlurpEvent);
    }
}
