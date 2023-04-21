package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class SniperEnchantment extends Enchantment {
    public SniperEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public String registryName() {
        return "sniper";
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        float distance = user.distanceTo(target);
        if (distance > 10) {
            target.damage(DamageSource.GENERIC, (float) level * (distance / 2.5F));
        }

        super.onTargetDamaged(user, target, level);
    }
}
