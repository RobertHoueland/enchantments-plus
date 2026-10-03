package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentLookup;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.SharedStates;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Unique
    private int jumpCount = 0;
    @Unique
    private boolean jumpedLastTick = false;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        // Dual Leap
        if (player.isOnGround() || player.isClimbing()) {
            jumpCount = EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.DUALLEAP, player);
        } else if (!jumpedLastTick && jumpCount > 0 && player.getVelocity().y < 0) {
            if (player.fallDistance < 4.0f && player.input.playerInput.jump() && canJump(player) && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableDualLeap) {
                jumpCount--;
                player.jump();

                SharedStates.dualLeapSuccessful = true;
            } else if (player.fallDistance >= 4.0f) {
                // player had fallen too many blocks, this prevents negating fall damage
                SharedStates.dualLeapFailed = true;
            }
        }

        jumpedLastTick = player.input.playerInput.jump();
    }

    @Unique
    private boolean canJump(ClientPlayerEntity player) {
        ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
        boolean wearingUsableElytra = LivingEntity.canGlideWith(chestItemStack, EquipmentSlot.CHEST);

        return !wearingUsableElytra && !player.isGliding() && !player.hasVehicle()
                && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION)
                && !player.getAbilities().creativeMode && !player.getAbilities().flying;
    }
}

