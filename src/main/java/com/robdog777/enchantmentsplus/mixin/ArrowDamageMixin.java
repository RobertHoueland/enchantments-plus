package com.robdog777.enchantmentsplus.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public class ArrowDamageMixin {
    @WrapOperation(method = "onHitEntity", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;hurtOrSimulate(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean applySniperDamage(Entity victim, DamageSource source, float damage, Operation<Boolean> original) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        var weapon = arrow.getWeaponItem();
        if (arrow.level() instanceof ServerLevel world && weapon != null) {
            MutableFloat bonus = new MutableFloat();
            LivingEntity attacker = source.getEntity() instanceof LivingEntity living ? living : null;
            EnchantmentHelper.runIterationOnItem(weapon, (entry, level) -> {
                Enchantment enchantment = entry.value();
                if (!enchantment.matchingSlot(EquipmentSlot.MAINHAND)) {
                    return;
                }
                for (var effect : enchantment.getEffects(EnchantmentEffectComponents.POST_ATTACK)) {
                    if (effect.enchanted() == EnchantmentTarget.ATTACKER && effect.affected() == EnchantmentTarget.VICTIM
                            && effect.effect() instanceof EnchantmentEffects custom && custom.action().equals("sniper")
                            && effect.matches(Enchantment.damageContext(world, level, victim, source))) {
                        bonus.add(custom.damageBonus(world, level, attacker, victim));
                    }
                }
            });
            // Vanilla has already applied arrow speed and critical scaling here.
            // Keep Sniper's bonus/cap in hit damage units, before target mitigation.
            damage += bonus.floatValue();
        }
        return original.call(victim, source, damage);
    }
}
