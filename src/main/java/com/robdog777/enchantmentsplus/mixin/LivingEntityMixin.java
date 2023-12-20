package com.robdog777.enchantmentsplus.mixin;


import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.enchants.BlazeWalkerEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "applyMovementEffects", at = @At("HEAD"))
    protected void applyMovementEffects(BlockPos pos, CallbackInfo ci) {
        LivingEntity currentEntity = (LivingEntity) (Object) this;

        // Blaze Walker
        int blazeWalkerLevel = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.BLAZEWALKER, currentEntity);
        if (blazeWalkerLevel > 0 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableBlazeWalker) {
            BlazeWalkerEnchantment.freezeLava(currentEntity, this.getWorld(), pos, blazeWalkerLevel);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    protected void tick(CallbackInfo ci) {
        LivingEntity currentEntity = (LivingEntity) (Object) this;

        // Lunar Sight
        int nightVisionLevel = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.LUNARSIGHT, currentEntity);
        if (nightVisionLevel > 0 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLunarSight) {
            // Stays for 11 seconds, otherwise sky flashes at <=10 seconds
            currentEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                    220, 0, false, false, true));
        }

        // Hiker
        int hikerLevel = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.HIKER, currentEntity);
        if (hikerLevel > 0 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableHiker) {
            this.stepHeight = hikerLevel + 0.1F;
        } else if (currentEntity instanceof PlayerEntity) {
            this.stepHeight = 0.6F;
        }

        // Excavator
        int excavatorLevel = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.EXCAVATOR, currentEntity);
        setRange(excavatorLevel > 0, excavatorLevel, currentEntity);

        // Moon Walker
        int moonWalkerLevel = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.MOONWALKER, currentEntity);
        if (moonWalkerLevel > 0 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableMoonWalker) {
            // constant effects
            currentEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20,
                    0, false, false, true));
            currentEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20,
                    0, false, false, true));

            // cool down for stronger effect
            if (!currentEntity.hasStatusEffect(EnchantmentsPlus.MOONREST)) {
                currentEntity.getWorld().playSound(null, currentEntity.getBlockPos(), EnchantmentsPlus.SwoopEvent,
                        currentEntity.getSoundCategory(), 1.0f, 1f);
                currentEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, moonWalkerLevel * 100,
                        moonWalkerLevel + 1, false, false, true));
                // 20 seconds
                int moonRestCooldown = 400;
                currentEntity.addStatusEffect(new StatusEffectInstance(EnchantmentsPlus.MOONREST,
                        moonRestCooldown, 0, false, false, true));
            }
        }
    }

    @Unique
    public void setRange(boolean change_range, int level, LivingEntity currentEntity) {
        if (change_range && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableExcavator) {
            Objects.requireNonNull(currentEntity.getAttributeInstance(ReachEntityAttributes.REACH)).setBaseValue(level);
            Objects.requireNonNull(currentEntity.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE)).setBaseValue(level);
        } else {
            Objects.requireNonNull(currentEntity.getAttributeInstance(ReachEntityAttributes.REACH)).setBaseValue(0.0);
            Objects.requireNonNull(currentEntity.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE)).setBaseValue(0.0);
        }
    }
}