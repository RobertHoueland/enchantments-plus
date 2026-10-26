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
        EnchantmentOption option = EnchantmentOption.byIndex(index);
        return option == null || option.isEnabled(CONFIG_HOLDER.getConfig());
    }

    public static int getMaxLevel(String id) {
        EnchantmentOption option = EnchantmentOption.byId(id);
        return option == null ? -1 : option.getMaxLevel(CONFIG_HOLDER.getConfig());
    }

    public static ConfigHolder<EnchantmentsPlusConfig> getConfigHolder() {
        return CONFIG_HOLDER;
    }
}
