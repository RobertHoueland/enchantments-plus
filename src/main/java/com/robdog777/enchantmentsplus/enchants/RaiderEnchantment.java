package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.world.World;

import java.util.Optional;

public class RaiderEnchantment extends Enchantment {
    public RaiderEnchantment() {
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
        return "raider";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return !(other instanceof DamageEnchantment) && other != EnchantmentsPlus.CUBICAL && other != EnchantmentsPlus.ENDSLAYER;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        World world = user.getEntityWorld();
        if (target instanceof LivingEntity && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableRaider) {
            if (target instanceof IllagerEntity || target instanceof WitchEntity ||
                    target instanceof VexEntity || target instanceof RavagerEntity) {
                target.damage(world.getDamageSources().generic(), (float) level * 5F);
            }
        } else {
            super.onTargetDamaged(user, target, level);
        }
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