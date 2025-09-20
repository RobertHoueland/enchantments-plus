package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Optional;

public class HikerEnchantment extends Enchantment {
    public HikerEnchantment() {
//        Rarity.UNCOMMON
        super(new Properties(
                ItemTags.FOOT_ARMOR_ENCHANTABLE,
                Optional.ofNullable(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                1,
                1,
                Enchantment.constantCost(5),
                Enchantment.constantCost(41),
                8,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.FEET}));
    }

    public String registryName() {
        return "hiker";
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableHiker;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableHiker;
    }
}
