package com.robdog777.enchantmentsplus;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

public final class EnchantmentLookup {
    private EnchantmentLookup() {
    }

    public static Holder<Enchantment> getEntry(Level world, ResourceKey<Enchantment> key) {
        return world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(key).orElse(null);
    }

    public static int getEquipmentLevel(ResourceKey<Enchantment> key, LivingEntity entity) {
        Holder<Enchantment> entry = getEntry(entity.level(), key);
        return entry == null ? 0 : EnchantmentHelper.getEnchantmentLevel(entry, entity);
    }

    public static int getLevel(ResourceKey<Enchantment> key, ItemInstance stack, Level world) {
        Holder<Enchantment> entry = getEntry(world, key);
        return entry == null ? 0 : EnchantmentHelper.getItemEnchantmentLevel(entry, stack);
    }
}
