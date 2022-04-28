package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.enchants.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnchantmentsPlus implements ModInitializer {
    private static final Enchantment FROSTBITE = Registry.register(
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

    private static final Enchantment PAYBACK = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "payback"),
            new PaybackEnchantment()
    );

    private static final Enchantment LIFESTEAL = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "lifesteal"),
            new LifeStealEnchantment()
    );

    private static final Enchantment THUNDERLORD = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "thunderlord"),
            new ThunderlordEnchantment()
    );

    private static final Enchantment LEVITATION = Registry.register(
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

    @Override
    public void onInitialize() {

    }
}
