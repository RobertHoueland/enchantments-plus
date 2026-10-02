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

public class SniperEnchantment extends Enchantment {
    public SniperEnchantment() {
//        Rarity.COMMON
        super(new Properties(
                ItemTags.BOW_ENCHANTABLE,
                Optional.ofNullable(ItemTags.BOW_ENCHANTABLE),
                3,
                3,
                Enchantment.leveledCost(12, 20),
                Enchantment.leveledCost(37, 20),
                4,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
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
        World world = user.getEntityWorld();
        float distance = user.distanceTo(target);
        if (distance > 10 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableSniper) {
            float damage = (float) level * (distance / 2.5F);
            if (damage > 40) {
                target.damage(world.getDamageSources().generic(), 40);
            } else {
                target.damage(world.getDamageSources().generic(), damage);
            }
        }
        super.onTargetDamaged(user, target, level);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableSniper;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableSniper;
    }
}
