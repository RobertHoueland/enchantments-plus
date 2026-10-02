package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public final class BlazeWalkerEnchantment {
    private BlazeWalkerEnchantment() {
    }

    public static void freezeLava(LivingEntity entity, ServerWorld world, BlockPos blockPos, int level) {
        if (!entity.isOnGround()) {
            return;
        }

        BlockState obsidian = Blocks.OBSIDIAN.getDefaultState();
        int radius = Math.min(16, 2 + level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos pos : BlockPos.iterate(blockPos.add(-radius, -1, -radius), blockPos.add(radius, -1, radius))) {
            if (!pos.isWithinDistance(entity.getPos(), radius)) {
                continue;
            }

            mutable.set(pos.getX(), pos.getY() + 1, pos.getZ());
            if (!world.getBlockState(mutable).isAir()) {
                continue;
            }

            BlockState lava = world.getBlockState(pos);
            Block block = lava.getBlock();
            if (lava.isOf(Blocks.LAVA) && block instanceof FluidBlock
                    && lava.get(FluidBlock.LEVEL) == 0
                    && obsidian.canPlaceAt(world, pos)
                    && world.canPlace(obsidian, pos, ShapeContext.absent())) {
                world.setBlockState(pos, obsidian);
                world.scheduleBlockTick(pos, Blocks.OBSIDIAN, MathHelper.nextInt(entity.getRandom(), 60, 120));
            }
        }
    }
}
