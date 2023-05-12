package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlazeWalkerEnchantment extends Enchantment {
    public BlazeWalkerEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    // skeleton code is from FrostWalker enchant
    public static void freezeLava(LivingEntity entity, World world, BlockPos blockPos, int level) {
        if (entity.isOnGround()) {
            BlockState blockState = Blocks.OBSIDIAN.getDefaultState();
            int f = Math.min(16, 2 + level);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-f, -1, -f), blockPos.add(f, -1, f))) {
                if (blockPos2.isWithinDistance(entity.getPos(), f)) {
                    mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
                    BlockState blockState2 = world.getBlockState(mutable);
                    if (blockState2.isAir()) {
                        BlockState blockState3 = world.getBlockState(blockPos2);
                        if (blockState3.getMaterial() == Material.LAVA && blockState3.get(FluidBlock.LEVEL) == 0 && blockState.canPlaceAt(world, blockPos2) && world.canPlace(blockState, blockPos2, ShapeContext.absent())) {
                            world.setBlockState(blockPos2, blockState);
                            world.scheduleBlockTick(blockPos2, Blocks.OBSIDIAN, MathHelper.nextInt(entity.getRandom(), 60, 120));
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }

    @Override
    public int getMaxLevel() {
        return 2;
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
}