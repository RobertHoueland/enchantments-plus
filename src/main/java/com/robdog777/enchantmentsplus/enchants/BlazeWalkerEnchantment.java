package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Optional;

public class BlazeWalkerEnchantment extends Enchantment {
    public BlazeWalkerEnchantment() {
//        Rarity.RARE
        super(new Properties(
                ItemTags.FOOT_ARMOR_ENCHANTABLE,
                Optional.ofNullable(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                2,
                2,
                Enchantment.leveledCost(10, 10),
                Enchantment.leveledCost(25, 10),
                4,
                FeatureSet.empty(),
                new EquipmentSlot[]{EquipmentSlot.FEET}));
    }

    public static void freezeLava(LivingEntity entity, World world, BlockPos blockPos, int level) {
        // original code is from Frost Walker
        if (entity.isOnGround() && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableBlazeWalker) {
            BlockState blockState = Blocks.OBSIDIAN.getDefaultState();
            int f = Math.min(16, 2 + level);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-f, -1, -f), blockPos.add(f, -1, f))) {
                if (blockPos2.isWithinDistance(entity.getPos(), f)) {
                    mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
                    BlockState blockState2 = world.getBlockState(mutable);
                    if (blockState2.isAir()) {
                        BlockState blockState3 = world.getBlockState(blockPos2);
                        Block block = blockState3.getBlock();
                        if (blockState3.isOf(Blocks.LAVA) && block instanceof FluidBlock && blockState3.get(FluidBlock.LEVEL) == 0 && blockState.canPlaceAt(world, blockPos2) && world.canPlace(blockState, blockPos2, ShapeContext.absent())) {
                            world.setBlockState(blockPos2, blockState);
                            world.scheduleBlockTick(blockPos2, Blocks.OBSIDIAN, MathHelper.nextInt(entity.getRandom(), 60, 120));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    public String registryName() {
        return "blazewalker";
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.DEPTH_STRIDER && other != Enchantments.FROST_WALKER;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableBlazeWalker;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableBlazeWalker;
    }
}