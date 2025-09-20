package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Optional;

public class MoonWalkerEnchantment extends Enchantment {
    public MoonWalkerEnchantment() {
//        Rarity.RARE
        super(new Properties(
                ItemTags.FOOT_ARMOR_ENCHANTABLE,
                Optional.ofNullable(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                1,
                2,
                Enchantment.leveledCost(10, 10),
                Enchantment.leveledCost(45, 10),
                8,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.FEET}));
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != EnchantmentsPlus.DUALLEAP;
    }

    public String registryName() {
        return "moonwalker";
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableMoonWalker;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableMoonWalker;
    }
}
