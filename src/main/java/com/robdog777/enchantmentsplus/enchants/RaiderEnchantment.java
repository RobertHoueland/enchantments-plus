package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class RaiderEnchantment extends Enchantment {
    public RaiderEnchantment() {
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
        return "raider";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return !(other instanceof DamageEnchantment) && other != EnchantmentsPlus.CUBICAL && other != EnchantmentsPlus.ENDSLAYER;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        World world = user.getEntityWorld();
        if (target instanceof LivingEntity livingEntity && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableRaider) {
            if (livingEntity.getGroup() == EntityGroup.ILLAGER) {
                target.damage(world.getDamageSources().generic(), (float) level * 2.5F);
            }
        }

        super.onTargetDamaged(user, target, level);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableRaider;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableRaider;
    }
}