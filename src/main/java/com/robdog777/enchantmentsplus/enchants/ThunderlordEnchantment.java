package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
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
        // skeleton code is from channeling

        Random rand = new Random();
        int random = rand.nextInt(10);
        BlockPos blockPos = target.getBlockPos(); // does this need to be user?
        if (random < level && target.getWorld().isSkyVisible(blockPos) && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableThunderlord) {
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(target.getWorld());
            assert lightningEntity != null;
            lightningEntity.refreshPositionAfterTeleport(target.getX(), target.getY(), target.getZ());
            target.getWorld().spawnEntity(lightningEntity);
        }
        super.onTargetDamaged(user, target, level);

    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableThunderlord;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableThunderlord;
    }
}