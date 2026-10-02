package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.world.World;

import java.util.Optional;

public class EndSlayerEnchantment extends Enchantment {
    public EndSlayerEnchantment() {
//        Rarity.UNCOMMON
        super(new Properties(
                ItemTags.WEAPON_ENCHANTABLE,
                Optional.ofNullable(ItemTags.SWORD_ENCHANTABLE),
                5,
                5,
                Enchantment.leveledCost(5, 8),
                Enchantment.leveledCost(25, 8),
                2,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
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
        World world = user.getEntityWorld();
        if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableEndSlayer) {
            if (target instanceof EndermanEntity || target instanceof EndermiteEntity || target instanceof ShulkerEntity) {
                target.damage(world.getDamageSources().generic(), (float) level * 5F);
            }

            if (target instanceof EnderDragonEntity) {
                target.damage(world.getDamageSources().generic(), (float) level * 10F);
            }
        } else {
            super.onTargetDamaged(user, target, level);
        }
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableEndSlayer;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableEndSlayer;
    }
}