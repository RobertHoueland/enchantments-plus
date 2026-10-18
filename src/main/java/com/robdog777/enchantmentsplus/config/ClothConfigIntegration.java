package com.robdog777.enchantmentsplus.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

// Only access this class when Cloth Config is installed
public final class ClothConfigIntegration {
    private static final ConfigHolder<EnchantmentsPlusConfig> CONFIG_HOLDER = AutoConfig.register(
            EnchantmentsPlusConfig.class, JanksonConfigSerializer::new);

    private ClothConfigIntegration() {
    }

    public static void load() {
        CONFIG_HOLDER.load();
    }

    public static boolean isEnchantmentEnabled(int index) {
        EnchantmentsPlusConfig config = CONFIG_HOLDER.getConfig();
        return switch (index) {
            case 0 -> config.enableBlazeWalker;
            case 1 -> config.enableCubical;
            case 2 -> config.enableDualLeap;
            case 3 -> config.enableEndSlayer;
            case 4 -> config.enableExcavator;
            case 5 -> config.enableFlashForge;
            case 6 -> config.enableFrostbite;
            case 7 -> config.enableHiker;
            case 8 -> config.enableLevitation;
            case 9 -> config.enableLifeSteal;
            case 10 -> config.enableLunarSight;
            case 11 -> config.enableMoonWalker;
            case 12 -> config.enableMysticMind;
            case 13 -> config.enablePayback;
            case 14 -> config.enableRaider;
            case 15 -> config.enableSniper;
            case 16 -> config.enableStormStrike;
            case 17 -> config.enableThunderlord;
            case 18 -> config.enableToxicStrike;
            default -> true;
        };
    }
}
