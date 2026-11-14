package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityDamageEffectsMixin {
    @Inject(method = "applyDamageEffects", at = @At("TAIL"))
    private void postMobAttack(LivingEntity attacker, Entity target, CallbackInfo ci) {
        // Shared by successful mob melee hits, including subclasses that override tryAttack.
        // Exclude projectile callbacks: their owner's current hand is not the firing weapon.
        if ((Object) this == attacker && attacker instanceof MobEntity mob) {
            EnchantmentEffects.postAttack(mob.getMainHandStack(), mob, target);
        }
    }
}
