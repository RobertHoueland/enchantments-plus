package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerDamageMixin {
    @Unique
    private float enchantmentsplus$attackCooldown;

    @Inject(method = "attack", at = @At("HEAD"))
    private void captureCooldown(Entity target, CallbackInfo ci) {
        enchantmentsplus$attackCooldown = ((PlayerEntity) (Object) this).getAttackCooldownProgress(0.5F);
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean primaryHit(Entity target, DamageSource source, float damage) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        float bonus = EnchantmentEffects.damageBonus(player.getMainHandStack(), player, target);
        return target.damage(source, damage + bonus * enchantmentsplus$attackCooldown);
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean sweepHit(LivingEntity target, DamageSource source, float damage) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        // Do not let the primary victim's mob-specific bonus leak into the shared sweeping base.
        return target.damage(source, damage + EnchantmentEffects.damageBonus(player.getMainHandStack(), player, target));
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V", shift = At.Shift.AFTER))
    private void postAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        EnchantmentEffects.postAttack(player.getMainHandStack(), player, target);
    }
}
