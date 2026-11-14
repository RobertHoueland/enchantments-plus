package com.robdog777.enchantmentsplus.config;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.enchants.ConfigurableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.server.MinecraftServer;

/** Does not load Cloth Config classes when that optional dependency is absent. */
public final class EnchantmentSettings {
    private static volatile Snapshot synced;
    private static volatile MinecraftServer server;

    private EnchantmentSettings() { }

    public record Snapshot(long enabledMask, int[] maxLevels) {
        public Snapshot {
            maxLevels = maxLevels.clone();
        }
        @Override
        public int[] maxLevels() { return maxLevels.clone(); }
    }

    public static void setServer(MinecraftServer value) { server = value; }
    public static void sync(Snapshot value) { synced = value; }
    public static void clear() { synced = null; }

    private static Snapshot clientSettings() {
        MinecraftServer currentServer = server;
        // The integrated server must never use its client's stale synchronized settings.
        return currentServer != null && currentServer.isOnThread() ? null : synced;
    }

    private static int index(Enchantment enchantment) {
        Enchantment[] entries = EnchantmentsPlus.ENCHANTMENTS;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == enchantment) return i;
        }
        return -1;
    }

    public static boolean enabled(Enchantment enchantment) {
        int index = index(enchantment);
        if (index < 0) return true;
        Snapshot settings = clientSettings();
        return settings != null ? (settings.enabledMask & (1L << index)) != 0
                : !EnchantmentsPlus.CLOTH_CONFIG_LOADED || ClothConfigIntegration.isEnchantmentEnabled(index);
    }

    public static int maxLevel(ConfigurableEnchantment enchantment) {
        Snapshot settings = clientSettings();
        int index = index(enchantment);
        if (settings != null && index >= 0) return settings.maxLevels[index];
        return EnchantmentsPlus.CLOTH_CONFIG_LOADED ? ClothConfigIntegration.getMaxLevel(enchantment.id())
                : enchantment.defaultMaxLevel();
    }

    public static Snapshot localSnapshot() {
        long mask = 0;
        Enchantment[] entries = EnchantmentsPlus.ENCHANTMENTS;
        int[] levels = new int[entries.length];
        for (int i = 0; i < entries.length; i++) {
            if (!EnchantmentsPlus.CLOTH_CONFIG_LOADED || ClothConfigIntegration.isEnchantmentEnabled(i)) mask |= 1L << i;
            ConfigurableEnchantment enchantment = (ConfigurableEnchantment) entries[i];
            levels[i] = EnchantmentsPlus.CLOTH_CONFIG_LOADED ? ClothConfigIntegration.getMaxLevel(enchantment.id())
                    : enchantment.defaultMaxLevel();
        }
        return new Snapshot(mask, levels);
    }
}
