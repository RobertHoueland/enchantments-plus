package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantment.class)
public class EnchantmentDamageMixin {
    @Inject(method = "doPostAttack(Lnet/minecraft/world/item/enchantment/TargetedConditionalEffect;"
            + "Lnet/minecraft/server/level/ServerLevel;ILnet/minecraft/world/item/enchantment/EnchantedItemInUse;"
            + "Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)V",
            at = @At("HEAD"), cancellable = true)
    private static void skipMigratedDamage(TargetedConditionalEffect<EnchantmentEntityEffect> effect,
                                           ServerLevel world, int level, EnchantedItemInUse item,
                                           Entity victim, DamageSource source, CallbackInfo ci) {
        if (effect.enchanted() == EnchantmentTarget.ATTACKER && effect.affected() == EnchantmentTarget.VICTIM
                && effect.effect() instanceof EnchantmentEffects custom && custom.isDamageBonus()) {
            ci.cancel();
        }
    }

    @Inject(method = "modifyDamage", at = @At("RETURN"))
    private void applyCustomDamage(ServerLevel world, int level, ItemStack stack, Entity victim,
                                   DamageSource source, MutableFloat amount, CallbackInfo ci) {
        Enchantment enchantment = (Enchantment) (Object) this;
        if (!enchantment.matchingSlot(EquipmentSlot.MAINHAND)) {
            return;
        }
        // Apply damage bonuses to the original hit rather than a second hit
        for (var effect : enchantment.getEffects(EnchantmentEffectComponents.POST_ATTACK)) {
            if (effect.enchanted() == EnchantmentTarget.ATTACKER && effect.affected() == EnchantmentTarget.VICTIM
                    && effect.effect() instanceof EnchantmentEffects custom && custom.isDamageBonus()
                    // Arrow Sniper damage is added after velocity and critical scaling.
                    && !(custom.action().equals("sniper") && source.getDirectEntity() instanceof AbstractArrow)
                    && effect.matches(Enchantment.damageContext(world, level, victim, source))) {
                LivingEntity attacker = source.getEntity() instanceof LivingEntity living ? living : null;
                amount.add(custom.damageBonus(world, level, attacker, victim));
            }
        }
    }
}
