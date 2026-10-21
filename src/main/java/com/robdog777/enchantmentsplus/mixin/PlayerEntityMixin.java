package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentLookup;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerEntityMixin {
    // Excavator
    @Inject(method = "blockInteractionRange", at = @At("HEAD"), cancellable = true)
    private void modifyBlockReach(CallbackInfoReturnable<Double> cir) {
        LivingEntity currentEntity = (LivingEntity) (Object) this;

        if (currentEntity instanceof Player) {
            double currentValue = currentEntity.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
            int excavatorLevel = EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.EXCAVATOR, currentEntity);

            if (excavatorLevel > 0 && EnchantmentsPlus.isEnchantmentEnabled(
                    EnchantmentsPlus.EXCAVATOR, currentEntity.level())) {
                cir.setReturnValue(currentValue + excavatorLevel);
            }
        }
    }
}
