package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class PaybackEnchantment extends Enchantment {
    public PaybackEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 6 * level;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public String registryName() {
        return "payback";
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(user.getHealth() < 10){
            target.damage(DamageSource.GENERIC, (float)level * 0.5F * (20 - user.getHealth()));
        }

        super.onTargetDamaged(user, target, level);
    }
}