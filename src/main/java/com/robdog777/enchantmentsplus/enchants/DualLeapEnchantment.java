package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Optional;

public class DualLeapEnchantment extends Enchantment {
    public DualLeapEnchantment() {
//        Rarity.RARE
        super(new Properties(
                ItemTags.FOOT_ARMOR_ENCHANTABLE,
                Optional.ofNullable(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                1,
                1,
                Enchantment.leveledCost(10, 10),
                Enchantment.leveledCost(25, 10),
                8,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.FEET}));
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != EnchantmentsPlus.MOONWALKER;
    }

    public String registryName() {
        return "dualleap";
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableDualLeap;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableDualLeap;
    }
}
