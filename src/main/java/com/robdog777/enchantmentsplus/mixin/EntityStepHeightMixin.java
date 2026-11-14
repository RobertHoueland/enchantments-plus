package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.config.EnchantmentSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class EntityStepHeightMixin {
    @Inject(method = "getStepHeight", at = @At("RETURN"), cancellable = true)
    private void hikerStepHeight(CallbackInfoReturnable<Float> cir) {
        if ((Object) this instanceof PlayerEntity player && EnchantmentSettings.enabled(EnchantmentsPlus.HIKER)) {
            int level = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.HIKER, player);
            if (level > 0) cir.setReturnValue(Math.max(cir.getReturnValue(), level + 0.1F));
        }
    }
}
