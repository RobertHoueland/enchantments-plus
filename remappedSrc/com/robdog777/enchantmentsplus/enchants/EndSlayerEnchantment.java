package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.ShulkerEntity;

public class EndSlayerEnchantment extends Enchantment {
    public EndSlayerEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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
        return 5;
    }

    public String registryName() {
        return "endslayer";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return !(other instanceof DamageEnchantment) && other != EnchantmentsPlus.CUBICAL && other != EnchantmentsPlus.RAIDER;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof EndermanEntity || target instanceof EndermiteEntity || target instanceof ShulkerEntity) {
            target.damage(DamageSource.GENERIC, (float) level * 2.5F);
        }

        if (target instanceof EnderDragonEntity) {
            target.damage(DamageSource.GENERIC, (float) level * 5F);
        }

        super.onTargetDamaged(user, target, level);
    }
}