package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public class BlockMixin {
    // smelt with FlashForge
    @Inject(method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;",
            at = @At("RETURN"), cancellable = true)
    private static void getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack, CallbackInfoReturnable<List<ItemStack>> cir) {
        List<ItemStack> items = new ArrayList<>();
        List<ItemStack> returnValue = cir.getReturnValue();

        // Flash Forge
        if (EnchantmentHelper.getLevel(EnchantmentsPlus.FLASHFORGE, stack) == 0
                || !EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableFlashForge) {
            cir.setReturnValue(returnValue);
            return;
        }
        for (ItemStack itemStack : returnValue) {
            Optional<RecipeEntry<SmeltingRecipe>> recipe = world.getRecipeManager()
                    .listAllOfType(RecipeType.SMELTING).stream()
                    .filter((smeltingRecipe -> smeltingRecipe.value().getIngredients().get(0).test(itemStack))).findFirst();
            if (recipe.isPresent()) {
                DynamicRegistryManager registryManager = world.getRegistryManager();
                ItemStack smelted = recipe.get().value().getResult(registryManager);
                smelted.setCount(itemStack.getCount());
                items.add(smelted);

                // Spawn XP orbs
                int xpAmount = (int) recipe.get().value().getExperience();
                world.spawnEntity(new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(), xpAmount));
            } else {
                items.add(itemStack);
            }
        }

        cir.setReturnValue(items);
    }
}
