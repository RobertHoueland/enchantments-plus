package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class HikerEnchantment extends Enchantment {
    public HikerEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinPower(int level) {
        return 15 * level;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    public String registryName() {
        return "hiker";
    }
}
