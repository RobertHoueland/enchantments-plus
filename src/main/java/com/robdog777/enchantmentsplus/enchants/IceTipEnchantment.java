package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class IceTipEnchantment extends Enchantment {

    public IceTipEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 5 * level;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public String registryName() {
        return "icetip";
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(target instanceof LivingEntity) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,
                    20 * 2 * level, level - 1));
        }

        super.onTargetDamaged(user, target, level);
    }
}