package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class PaybackEnchantment extends Enchantment {
    public PaybackEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 5 + (6 * level);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public String registryName() {
        return "payback";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != EnchantmentsPlus.LIFESTEAL;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        World world = user.getEntityWorld();
        if (user.getHealth() < 10 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enablePayback) {
            target.damage(world.getDamageSources().generic(), (float) level * 0.5F * (20 - user.getHealth()));
        }

        super.onTargetDamaged(user, target, level);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enablePayback;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enablePayback;
    }
}