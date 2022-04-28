package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;

import java.util.Random;

public class ThunderlordEnchantment extends Enchantment {
    public ThunderlordEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 10 * level;
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
        if(random < level) {
            LightningEntity lightningEntity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(target.world);
            assert lightningEntity != null;
            lightningEntity.refreshPositionAfterTeleport(target.getX(), target.getY(), target.getZ());
            target.world.spawnEntity(lightningEntity);
        }

        super.onTargetDamaged(user, target, level);
    }
}