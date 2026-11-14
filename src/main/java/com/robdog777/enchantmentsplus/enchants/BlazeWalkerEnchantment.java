package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public final class BlazeWalkerEnchantment {
    private BlazeWalkerEnchantment() { }

    // Based on vanilla Frost Walker; world changes are server-authoritative.
    public static void freezeLava(LivingEntity entity, ServerWorld world, BlockPos pos, int level) {
        if (!entity.isOnGround()) return;
        BlockState obsidian = Blocks.OBSIDIAN.getDefaultState();
        int radius = Math.min(16, 2 + level);
        for (BlockPos lavaPos : BlockPos.iterate(pos.add(-radius, -1, -radius), pos.add(radius, -1, radius))) {
            if (!lavaPos.isWithinDistance(entity.getPos(), radius) || !world.getBlockState(lavaPos.up()).isAir()) continue;
            BlockState lava = world.getBlockState(lavaPos);
            if (lava.isOf(Blocks.LAVA) && lava.get(FluidBlock.LEVEL) == 0
                    && obsidian.canPlaceAt(world, lavaPos) && world.canPlace(obsidian, lavaPos, ShapeContext.absent())) {
                world.setBlockState(lavaPos, obsidian);
                world.scheduleBlockTick(lavaPos, Blocks.OBSIDIAN, MathHelper.nextInt(entity.getRandom(), 60, 120));
            }
        }
    }
}
