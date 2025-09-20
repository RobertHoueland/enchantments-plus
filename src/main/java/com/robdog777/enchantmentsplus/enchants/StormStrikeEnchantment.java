package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class StormStrikeEnchantment extends Enchantment {
    public StormStrikeEnchantment() {
//        Rarity.VERY_RARE
        super(new Properties(
                ItemTags.BOW_ENCHANTABLE,
                Optional.ofNullable(ItemTags.BOW_ENCHANTABLE),
                8,
                3,
                Enchantment.leveledCost(12, 20),
                Enchantment.leveledCost(65, 20),
                8,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    }

    public String registryName() {
        return "stormstrike";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != EnchantmentsPlus.SNIPER;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        // code is from channeling
        Random rand = new Random();
        int random = rand.nextInt(20);
        BlockPos blockPos = target.getBlockPos();
        if (random < level && target.getWorld().isSkyVisible(blockPos) && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableStormStrike) {
            LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(target.getWorld());
            assert lightningEntity != null;
            lightningEntity.refreshPositionAfterTeleport(target.getX(), target.getY(), target.getZ());
            target.getWorld().spawnEntity(lightningEntity);
        } else {
            super.onTargetDamaged(user, target, level);
        }
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableStormStrike;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableStormStrike;
    }
}
