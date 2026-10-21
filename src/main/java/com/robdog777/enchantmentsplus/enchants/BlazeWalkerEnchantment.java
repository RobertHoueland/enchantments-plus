package com.robdog777.enchantmentsplus.enchants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public final class BlazeWalkerEnchantment {
    private BlazeWalkerEnchantment() {
    }

    public static void freezeLava(LivingEntity entity, ServerLevel world, BlockPos blockPos, int level) {
        if (!entity.onGround()) {
            return;
        }

        BlockState obsidian = Blocks.OBSIDIAN.defaultBlockState();
        int radius = Math.min(16, 2 + level);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-radius, -1, -radius), blockPos.offset(radius, -1, radius))) {
            if (!pos.closerToCenterThan(entity.position(), radius)) {
                continue;
            }

            mutable.set(pos.getX(), pos.getY() + 1, pos.getZ());
            if (!world.getBlockState(mutable).isAir()) {
                continue;
            }

            BlockState lava = world.getBlockState(pos);
            Block block = lava.getBlock();
            if (lava.is(Blocks.LAVA) && block instanceof LiquidBlock
                    && lava.getValue(LiquidBlock.LEVEL) == 0
                    && obsidian.canSurvive(world, pos)
                    && world.isUnobstructed(obsidian, pos, CollisionContext.empty())) {
                world.setBlockAndUpdate(pos, obsidian);
                world.scheduleTick(pos, Blocks.OBSIDIAN, Mth.nextInt(entity.getRandom(), 60, 120));
            }
        }
    }
}
