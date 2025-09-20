package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Optional;

public class LunarSightEnchantment extends Enchantment {
    public LunarSightEnchantment() {
//        Rarity.COMMON
        super(new Properties(
                ItemTags.HEAD_ARMOR_ENCHANTABLE,
                Optional.ofNullable(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                1,
                1,
                Enchantment.constantCost(1),
                Enchantment.constantCost(41),
                4,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.HEAD}));
    }

    public String registryName() {
        return "lunarsight";
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLunarSight;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLunarSight;
    }
}