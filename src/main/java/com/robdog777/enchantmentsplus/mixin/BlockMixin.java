package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentLookup;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Block.class)
public class BlockMixin {
    // smelt with FlashForge
    @Inject(method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemInstance;)Ljava/util/List;",
            at = @At("RETURN"))
    private static void getDroppedStacks(BlockState state, ServerLevel world, BlockPos pos, BlockEntity blockEntity,
                                         Entity entity, ItemInstance stack, CallbackInfoReturnable<List<ItemStack>> cir) {
        List<ItemStack> returnValue = cir.getReturnValue();

        // Flash Forge
        if (EnchantmentLookup.getLevel(EnchantmentsPlus.FLASHFORGE, stack, world) == 0
                || !EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.FLASHFORGE, world)) {
            return;
        }

        for (int i = 0; i < returnValue.size(); i++) {
            ItemStack itemStack = returnValue.get(i);
            SingleRecipeInput input = new SingleRecipeInput(itemStack);
            Optional<RecipeHolder<SmeltingRecipe>> recipe = world.recipeAccess()
                    .getRecipeFor(RecipeType.SMELTING, input, world);

            if (recipe.isPresent()) {
                ItemStack smelted = recipe.get().value().assemble(input).copy(); // copy to prevent duplicated drops
                smelted.setCount(itemStack.getCount());
                returnValue.set(i, smelted);

                // Spawn XP orbs
                int xpAmount = (int) recipe.get().value().experience();
                world.addFreshEntity(new ExperienceOrb(world, pos.getX(), pos.getY(), pos.getZ(), xpAmount));

                if (state.getBlock() == Blocks.IRON_ORE || state.getBlock() == Blocks.DEEPSLATE_IRON_ORE ||
                        state.getBlock() == Blocks.COPPER_ORE || state.getBlock() == Blocks.DEEPSLATE_COPPER_ORE) {
                    // these ores are hardcoded to drop XP since they work differently
                    // technically smelting ore gives 0.7 XP but we're rounding for an int
                    world.addFreshEntity(new ExperienceOrb(world, pos.getX(), pos.getY(), pos.getZ(), 1));
                }
            }
        }
    }
}
