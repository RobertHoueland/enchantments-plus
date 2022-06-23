package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class ThunderlordEnchantment extends Enchantment {
    public ThunderlordEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 5 + (8 * level);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public String registryName() {
        return "thunderlord";
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        Random rand = new Random();
        int random = rand.nextInt(10);
        BlockPos blockPos = target.getBlockPos(); // does this need to be user?
        if (random < level && target.world.isSkyVisible(blockPos)) {
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(target.world);
            assert lightningEntity != null;
            lightningEntity.refreshPositionAfterTeleport(target.getX(), target.getY(), target.getZ());
            target.world.spawnEntity(lightningEntity);
        }

        super.onTargetDamaged(user, target, level);
    }
}