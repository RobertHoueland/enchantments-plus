package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentLookup;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.SharedStates;
import com.robdog777.enchantmentsplus.enchants.BlazeWalkerEnchantment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "onChangedBlock", at = @At("HEAD"))
    protected void applyMovementEffects(ServerLevel world, BlockPos pos, CallbackInfo ci) {
        LivingEntity currentEntity = (LivingEntity) (Object) this;
        int blazeWalkerLevel = EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.BLAZEWALKER, currentEntity);
        if (blazeWalkerLevel > 0 && EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.BLAZEWALKER, world)) {
            BlazeWalkerEnchantment.freezeLava(currentEntity, world, pos, blazeWalkerLevel);
        }
    }

    @Inject(method = "maxUpStep", at = @At("RETURN"), cancellable = true)
    private void applyHikerStepHeight(CallbackInfoReturnable<Float> cir) {
        LivingEntity currentEntity = (LivingEntity) (Object) this;
        if (currentEntity instanceof Player
                && EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.HIKER, currentEntity.level())) {
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
        if (currentEntity instanceof Player player) {
            if (SharedStates.dualLeapSuccessful) {
                player.level().playSound(null, player.blockPosition(), EnchantmentsPlus.WhooshEvent,
                        SoundSource.PLAYERS, 0.7f, 1f);
                SharedStates.dualLeapSuccessful = false;
            } else if (SharedStates.dualLeapFailed) {
                player.level().playSound(null, player.blockPosition(), EnchantmentsPlus.DenyEvent,
                        SoundSource.PLAYERS, 0.7f, 1f);
                SharedStates.dualLeapFailed = false;
            }
        }

        // Only process periodic enchantment effects every five ticks.
        if (currentEntity.tickCount % 5 == 0) {
            if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.LUNARSIGHT, currentEntity.level())
                    && EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.LUNARSIGHT, currentEntity) > 0) {
                currentEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,
                        220, 0, false, false, true));
            }

            if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.MOONWALKER, currentEntity.level())) {
                Holder<MobEffect> moonRestEntry = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(EnchantmentsPlus.MOONREST);
                int moonWalkerLevel = EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.MOONWALKER, currentEntity);
                if (moonWalkerLevel > 0) {
                    currentEntity.addEffect(new MobEffectInstance(MobEffects.SPEED,
                            20, 0, false, false, true));
                    currentEntity.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST,
                            20, 0, false, false, true));
                    if (!currentEntity.hasEffect(moonRestEntry)) {
                        currentEntity.level().playSound(null, currentEntity.blockPosition(),
                                EnchantmentsPlus.SwoopEvent, currentEntity.getSoundSource(), 1.0f, 1f);
                        currentEntity.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST,
                                moonWalkerLevel * 100, moonWalkerLevel + 1, false, false, true));
                        currentEntity.addEffect(new MobEffectInstance(moonRestEntry,
                                400, 0, false, false, true));
                    }
                }
            }
        }
    }
}
