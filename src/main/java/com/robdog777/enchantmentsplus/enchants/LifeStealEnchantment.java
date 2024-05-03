package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class LifeStealEnchantment extends Enchantment {
    public LifeStealEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 5 + (7 * level);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public String registryName() {
        return "lifesteal";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != EnchantmentsPlus.PAYBACK;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        World world = user.getWorld();
        if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLifeSteal && !world.isClient()) {
            if (user.getHealth() < 20 && (Math.random() < 0.10 + (level * 0.20)) && target instanceof LivingEntity) {
                float targetHealth = ((LivingEntity) target).getHealth();
                if (targetHealth > 0) {
                    world.playSound(null, user.getBlockPos(), EnchantmentsPlus.BlurpEvent, SoundCategory.PLAYERS, 1.0f, 1f);
                    user.heal(targetHealth * 0.5f);
                }
            }
        }
        super.onTargetDamaged(user, target, level);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLifeSteal;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLifeSteal;
    }
}