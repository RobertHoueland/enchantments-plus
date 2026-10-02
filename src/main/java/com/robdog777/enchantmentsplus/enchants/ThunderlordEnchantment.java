package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;

public class ThunderlordEnchantment extends Enchantment {
    public ThunderlordEnchantment() {
//        Rarity.VERY_RARE
        super(new Properties(
                ItemTags.WEAPON_ENCHANTABLE,
                Optional.ofNullable(ItemTags.SWORD_ENCHANTABLE),
                8,
                3,
                Enchantment.leveledCost(12, 20),
                Enchantment.leveledCost(65, 20),
                8,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    }

    public String registryName() {
        return "thunderlord";
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        // code is from channeling
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