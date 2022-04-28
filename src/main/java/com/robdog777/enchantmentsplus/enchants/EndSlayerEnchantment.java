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
import net.minecraft.entity.mob.*;

public class EndSlayerEnchantment extends Enchantment {
    public EndSlayerEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 8 * level;
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
        return !(other instanceof DamageEnchantment) && other!= EnchantmentsPlus.CUBICAL;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(target instanceof EndermanEntity || target instanceof EndermiteEntity || target instanceof ShulkerEntity) {
            target.damage(DamageSource.GENERIC, level * 2.5F);
        }

        if(target instanceof EnderDragonEntity) {
            target.damage(DamageSource.GENERIC, level * 5F);
        }

        super.onTargetDamaged(user, target, level);
    }
}