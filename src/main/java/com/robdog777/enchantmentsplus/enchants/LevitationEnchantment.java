package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;

import java.util.Optional;

public class LevitationEnchantment extends Enchantment {
    public LevitationEnchantment() {
//        Rarity.RARE
        super(new Properties(
                ItemTags.WEAPON_ENCHANTABLE,
                Optional.ofNullable(ItemTags.SWORD_ENCHANTABLE),
                2,
                2,
                Enchantment.leveledCost(10, 20),
                Enchantment.leveledCost(60, 20),
                4,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
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
        if (target instanceof LivingEntity && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLevitation) {
            ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION,
                    20 * level, level - 1));
        }

        super.onTargetDamaged(user, target, level);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLevitation;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLevitation;
    }
}