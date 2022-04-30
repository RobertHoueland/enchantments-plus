package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
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
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world, GameOptions settings) {
        super(entityType, world);
    }

    long lastBoost;
    long cooldownTime = 400; // 20 seconds

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ItemStack itemStackHead = this.getEquippedStack(EquipmentSlot.HEAD);
        int nightvisionLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.LUNARSIGHT, itemStackHead);
        if (nightvisionLevel > 0) {
            // Stays for 11 seconds, otherwise sky flashes at <=10 seconds
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                    220, 0, false, false, true));
        }

        // boost while sprinting
        if (MinecraftClient.getInstance().options.sprintKey.isPressed()) {
            ItemStack itemStackFeet = this.getEquippedStack(EquipmentSlot.FEET);
            int moonwalkerLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.MOONWALKER, itemStackFeet);
            if (moonwalkerLevel > 0) {
                long time = world.getTimeOfDay();
                // cooldown
                if (time > lastBoost + cooldownTime) {
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,
                            moonwalkerLevel * 100, moonwalkerLevel - 1, false, false, true));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,
                            moonwalkerLevel * 100, 0, false, false, true));
                    lastBoost = time;
                }
            }
        }
    }
}