package com.robdog777.enchantmentsplus.mixin;

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

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private final int moonrestCooldown = 400; // 20 seconds

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ItemStack itemStackHead = this.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack itemStackFeet = this.getEquippedStack(EquipmentSlot.FEET);

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
                this.addStatusEffect(new StatusEffectInstance(EnchantmentsPlus.MOONREST,
                        moonrestCooldown, 0, false, false, true));
            }
        }
    }
}