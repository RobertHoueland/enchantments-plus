package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class LifeStealEnchantment extends Enchantment {
    public LifeStealEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 5 + (7 * level);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public String registryName() {
        return "lifesteal";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != EnchantmentsPlus.PAYBACK;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (user.getHealth() < 20 && (Math.random() < level * 0.25)) {
            user.heal(1.0F * level);
        }

        super.onTargetDamaged(user, target, level);
    }
}