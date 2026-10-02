package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Optional;

public class FlashForgeEnchantment extends Enchantment {
    public FlashForgeEnchantment() {
//        Rarity.RARE
        super(new Properties(
                ItemTags.MINING_LOOT_ENCHANTABLE,
                Optional.ofNullable(ItemTags.MINING_LOOT_ENCHANTABLE),
                1,
                1,
                Enchantment.leveledCost(10, 10),
                Enchantment.leveledCost(25, 10),
                8,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    }

    public String registryName() {
        return "flashforge";
    }

    public boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.SILK_TOUCH;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableFlashForge;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableFlashForge;
    }
}
