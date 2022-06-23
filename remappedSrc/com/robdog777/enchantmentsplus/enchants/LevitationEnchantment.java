package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class LevitationEnchantment extends Enchantment {
    public LevitationEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 20 * (level - 1);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    public String registryName() {
        return "levitation";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != Enchantments.FIRE_ASPECT && other != EnchantmentsPlus.TOXICSTRIKE && other != EnchantmentsPlus.FROSTBITE;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION,
                    20 * level, level - 1));
        }

        super.onTargetDamaged(user, target, level);
    }
}