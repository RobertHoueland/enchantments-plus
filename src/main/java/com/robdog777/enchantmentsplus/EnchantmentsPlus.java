package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.enchants.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnchantmentsPlus implements ModInitializer
{
    private static final Enchantment ICETIP = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "icetip"),
            new IceTipEnchantment()
    );

    public static final Enchantment NIGHTVISION = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "nightvision"),
            new NightVisionEnchantment()
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

    private static final Enchantment EXECUTE = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "execute"),
            new ExecuteEnchantment()
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

    public static final Enchantment AUTOSMELT = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("enchantmentsplus", "autosmelt"),
            new AutoSmeltEnchantment()
    );

    @Override
    public void onInitialize() {

    }
}
