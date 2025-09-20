package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.world.World;

import java.util.Optional;

public class PaybackEnchantment extends Enchantment {
    public PaybackEnchantment() {
//        Rarity.RARE
        super(new Properties(
                ItemTags.WEAPON_ENCHANTABLE,
                Optional.ofNullable(ItemTags.SWORD_ENCHANTABLE),
                3,
                3,
                Enchantment.leveledCost(5, 8),
                Enchantment.leveledCost(45, 8),
                5,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
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
        } else {
            super.onTargetDamaged(user, target, level);
        }
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