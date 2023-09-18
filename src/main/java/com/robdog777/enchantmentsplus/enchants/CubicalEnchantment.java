package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.world.World;

public class CubicalEnchantment extends Enchantment {
    public CubicalEnchantment() {
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
        return "cubical";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return !(other instanceof DamageEnchantment) && other != EnchantmentsPlus.ENDSLAYER && other != EnchantmentsPlus.RAIDER;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        World world = user.getEntityWorld();
        if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableCubical) {
            if (target instanceof CreeperEntity || target instanceof SlimeEntity) {
                target.damage(world.getDamageSources().generic(), (float) level * 2.5F);
            }
        }

        super.onTargetDamaged(user, target, level);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableCubical;
    }
    
    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableCubical;
    }
}