package com.robdog777.enchantmentsplus.mixin;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.config.EnchantmentSettings;
import com.robdog777.enchantmentsplus.enchants.BlazeWalkerEnchantment;
import com.robdog777.enchantmentsplus.enchants.ConfigurableEnchantment;
import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    private static final UUID EXCAVATOR_REACH = UUID.fromString("e3c8a840-bf90-48a2-962b-297878d7ba62");

    @Inject(method = "damage", at = @At("RETURN"))
    private void mysticMind(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (cir.getReturnValue() && entity.isAlive()) {
            int level = EnchantmentHelper.getLevel(EnchantmentsPlus.MYSTICMIND, entity.getEquippedStack(EquipmentSlot.HEAD));
            EnchantmentEffects.apply((ConfigurableEnchantment) EnchantmentsPlus.MYSTICMIND, entity, entity, level);
        }
    }

    @Inject(method = "applyMovementEffects", at = @At("HEAD"))
    private void applyMovementEffects(BlockPos pos, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        int level = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.BLAZEWALKER, entity);
        if (level > 0 && entity.getWorld() instanceof ServerWorld world && EnchantmentSettings.enabled(EnchantmentsPlus.BLAZEWALKER)) {
            BlazeWalkerEnchantment.freezeLava(entity, world, pos, level);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.getWorld().isClient || entity.age % 5 != 0) return;
        if (entity instanceof PlayerEntity) {
            int level = EnchantmentSettings.enabled(EnchantmentsPlus.EXCAVATOR)
                    ? EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.EXCAVATOR, entity) : 0;
            var reach = entity.getAttributeInstance(ReachEntityAttributes.REACH);
            if (reach != null) {
                var modifier = reach.getModifier(EXCAVATOR_REACH);
                if (modifier != null && modifier.getValue() != level) reach.removeModifier(EXCAVATOR_REACH);
                if (level > 0 && reach.getModifier(EXCAVATOR_REACH) == null) {
                    reach.addTemporaryModifier(new EntityAttributeModifier(EXCAVATOR_REACH, "Excavator block reach", level,
                            EntityAttributeModifier.Operation.ADDITION));
                }
            }
        }
        int nightVisionLevel = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.LUNARSIGHT, entity);
        if (nightVisionLevel > 0 && EnchantmentSettings.enabled(EnchantmentsPlus.LUNARSIGHT)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 220, 0, false, false, true));
        }
        int moonWalkerLevel = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.MOONWALKER, entity);
        if (moonWalkerLevel > 0 && EnchantmentSettings.enabled(EnchantmentsPlus.MOONWALKER)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 0, false, false, true));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 0, false, false, true));
            if (!entity.hasStatusEffect(EnchantmentsPlus.MOONREST)) {
                entity.getWorld().playSound(null, entity.getBlockPos(), EnchantmentsPlus.SwoopEvent, entity.getSoundCategory(), 1.0F, 1.0F);
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, moonWalkerLevel * 100, moonWalkerLevel + 1, false, false, true));
                entity.addStatusEffect(new StatusEffectInstance(EnchantmentsPlus.MOONREST, 400, 0, false, false, true));
            }
        }
    }
}
