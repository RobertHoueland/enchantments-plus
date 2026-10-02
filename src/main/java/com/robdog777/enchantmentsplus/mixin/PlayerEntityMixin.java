package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    // Excavator
    @Inject(method = "getBlockInteractionRange", at = @At("HEAD"), cancellable = true)
    private void modifyBlockReach(CallbackInfoReturnable<Double> cir) {
        LivingEntity currentEntity = (LivingEntity) (Object) this;

        if (currentEntity instanceof PlayerEntity) {
            double currentValue = currentEntity.getAttributeValue(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE);
            int excavatorLevel = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.EXCAVATOR, currentEntity);

            if (excavatorLevel > 0 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableExcavator) {
                cir.setReturnValue(currentValue + excavatorLevel);
            }
        }
    }
}
