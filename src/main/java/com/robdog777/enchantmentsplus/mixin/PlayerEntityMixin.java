package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.EnchantmentsPlusClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    int cooldownTime = 400; // 20 seconds

    // Only run on client
    @Environment(EnvType.CLIENT)
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

        // boost
        if (MinecraftClient.getInstance().options.sprintKey.isPressed()) {
            int moonwalkerLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.MOONWALKER, itemStackFeet);
            if (EnchantmentsPlusClient.pressed && moonwalkerLevel > 0) {
                // cooldown
                if (!this.hasStatusEffect(EnchantmentsPlus.MOONREST)) {
                    if (!world.isClient) {
                        world.playSound(null, this.getBlockPos(), EnchantmentsPlus.SwoopEvent, SoundCategory.PLAYERS, 0.7f, 1f);
                    }
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,
                            moonwalkerLevel * 100, moonwalkerLevel - 1, false, false, true));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,
                            moonwalkerLevel * 100, 0, false, false, true));
                    this.addStatusEffect(new StatusEffectInstance(EnchantmentsPlus.MOONREST,
                            cooldownTime, 0, false, false, true));
                }
            }
        }
    }
}