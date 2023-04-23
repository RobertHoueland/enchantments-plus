package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
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
    public int getMaxLevel() {
        return 3;
    }

    public String registryName() {
        return "sniper";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != EnchantmentsPlus.STORMSTRIKE;
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
