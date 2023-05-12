package com.robdog777.enchantmentsplus.mixin;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ItemStack itemStackHead = this.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack itemStackFeet = this.getEquippedStack(EquipmentSlot.FEET);
        ItemStack itemStackHand = this.getEquippedStack(EquipmentSlot.MAINHAND);

        int nightvisionLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.LUNARSIGHT, itemStackHead);
        if (nightvisionLevel > 0) {
            // Stays for 11 seconds, otherwise sky flashes at <=10 seconds
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                    220, 0, false, false, true));
        }

        int hikerLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.HIKER, itemStackFeet);
        if (hikerLevel > 0) {
            this.stepHeight = hikerLevel + 0.1F;
        } else {
            this.stepHeight = 0.6F;
        }

        int excavatorLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.EXCAVATOR, itemStackHand);
        setRange(excavatorLevel > 0, excavatorLevel);

        int moonwalkerLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.MOONWALKER, itemStackFeet);
        // moonwalk boost
        if (moonwalkerLevel > 0) {
            // constant effects
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20,
                    0, false, false, true));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20,
                    0, false, false, true));

            // cooldown for stronger effect
            if (!this.hasStatusEffect(EnchantmentsPlus.MOONREST)) {
                this.world.playSound(null, this.getBlockPos(), EnchantmentsPlus.SwoopEvent,
                        this.getSoundCategory(), 1.0f, 1f);
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, moonwalkerLevel * 100,
                        moonwalkerLevel + 1, false, false, true));
                // 20 seconds
                int moonrestCooldown = 400;
                this.addStatusEffect(new StatusEffectInstance(EnchantmentsPlus.MOONREST,
                        moonrestCooldown, 0, false, false, true));
            }
        }
    }

    public void setRange(boolean change_range, int level) {
        if (change_range) {
            Objects.requireNonNull(this.getAttributeInstance(ReachEntityAttributes.REACH)).setBaseValue(level);
            Objects.requireNonNull(this.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE)).setBaseValue(level);
        } else {
            Objects.requireNonNull(this.getAttributeInstance(ReachEntityAttributes.REACH)).setBaseValue(0.0);
            Objects.requireNonNull(this.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE)).setBaseValue(0.0);
        }
    }
}