package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentLookup;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.SharedStates;
import com.robdog777.enchantmentsplus.enchants.BlazeWalkerEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "applyMovementEffects", at = @At("HEAD"))
    protected void applyMovementEffects(ServerWorld world, BlockPos pos, CallbackInfo ci) {
        LivingEntity currentEntity = (LivingEntity) (Object) this;
        int blazeWalkerLevel = EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.BLAZEWALKER, currentEntity);
        if (blazeWalkerLevel > 0 && EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.BLAZEWALKER, world)) {
            BlazeWalkerEnchantment.freezeLava(currentEntity, world, pos, blazeWalkerLevel);
        }
    }

    @Inject(method = "getStepHeight", at = @At("RETURN"), cancellable = true)
    private void applyHikerStepHeight(CallbackInfoReturnable<Float> cir) {
        LivingEntity currentEntity = (LivingEntity) (Object) this;
        if (currentEntity instanceof PlayerEntity
                && EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.HIKER, currentEntity.getEntityWorld())) {
            int hikerLevel = EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.HIKER, currentEntity);
            if (hikerLevel > 0) {
                cir.setReturnValue(Math.max(cir.getReturnValue(), hikerLevel + 0.1F));
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    protected void tick(CallbackInfo ci) {
        LivingEntity currentEntity = (LivingEntity) (Object) this;

        // Dual Leap sound effects
        // TODO: play on servers
        if (currentEntity instanceof PlayerEntity player) {
            if (SharedStates.dualLeapSuccessful) {
                player.getEntityWorld().playSound(null, player.getBlockPos(), EnchantmentsPlus.WhooshEvent,
                        SoundCategory.PLAYERS, 0.7f, 1f);
                SharedStates.dualLeapSuccessful = false;
            } else if (SharedStates.dualLeapFailed) {
                player.getEntityWorld().playSound(null, player.getBlockPos(), EnchantmentsPlus.DenyEvent,
                        SoundCategory.PLAYERS, 0.7f, 1f);
                SharedStates.dualLeapFailed = false;
            }
        }

        // Only process periodic enchantment effects every five ticks.
        if (currentEntity.age % 5 == 0) {
            if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.LUNARSIGHT, currentEntity.getEntityWorld())
                    && EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.LUNARSIGHT, currentEntity) > 0) {
                currentEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                        220, 0, false, false, true));
            }

            if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.MOONWALKER, currentEntity.getEntityWorld())) {
                RegistryEntry<StatusEffect> moonRestEntry = Registries.STATUS_EFFECT.getEntry(EnchantmentsPlus.MOONREST);
                int moonWalkerLevel = EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.MOONWALKER, currentEntity);
                if (moonWalkerLevel > 0) {
                    currentEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,
                            20, 0, false, false, true));
                    currentEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,
                            20, 0, false, false, true));
                    if (!currentEntity.hasStatusEffect(moonRestEntry)) {
                        currentEntity.getEntityWorld().playSound(null, currentEntity.getBlockPos(),
                                EnchantmentsPlus.SwoopEvent, currentEntity.getSoundCategory(), 1.0f, 1f);
                        currentEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,
                                moonWalkerLevel * 100, moonWalkerLevel + 1, false, false, true));
                        currentEntity.addStatusEffect(new StatusEffectInstance(moonRestEntry,
                                400, 0, false, false, true));
                    }
                }
            }
        }
    }
}
