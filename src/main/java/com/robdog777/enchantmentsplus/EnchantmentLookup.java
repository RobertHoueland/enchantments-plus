package com.robdog777.enchantmentsplus;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public final class EnchantmentLookup {
    private EnchantmentLookup() {
    }

    public static RegistryEntry<Enchantment> getEntry(World world, RegistryKey<Enchantment> key) {
        return world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(key).orElse(null);
    }

    public static int getEquipmentLevel(RegistryKey<Enchantment> key, LivingEntity entity) {
        RegistryEntry<Enchantment> entry = getEntry(entity.getWorld(), key);
        return entry == null ? 0 : EnchantmentHelper.getEquipmentLevel(entry, entity);
    }

    public static int getLevel(RegistryKey<Enchantment> key, ItemStack stack, World world) {
        RegistryEntry<Enchantment> entry = getEntry(world, key);
        return entry == null ? 0 : EnchantmentHelper.getLevel(entry, stack);
    }
}
