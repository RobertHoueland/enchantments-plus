package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentLookup;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.SharedStates;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class ClientPlayerEntityMixin {
    @Unique
    private int jumpCount = 0;
    @Unique
    private boolean jumpedLastTick = false;

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        LocalPlayer player = (LocalPlayer) (Object) this;

        // Dual Leap
        if (player.onGround() || player.onClimbable()) {
            jumpCount = EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.DUALLEAP, player);
        } else if (!jumpedLastTick && jumpCount > 0 && player.getDeltaMovement().y < 0) {
            if (player.fallDistance < 4.0f && player.input.keyPresses.jump() && canJump(player)
                    && EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.DUALLEAP, player.level())) {
                jumpCount--;
                player.jumpFromGround();

                SharedStates.dualLeapSuccessful = true;
            } else if (player.fallDistance >= 4.0f) {
                // player had fallen too many blocks, this prevents negating fall damage
                SharedStates.dualLeapFailed = true;
            }
        }

        jumpedLastTick = player.input.keyPresses.jump();
    }

    @Unique
    private boolean canJump(LocalPlayer player) {
        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        boolean wearingUsableElytra = LivingEntity.canGlideUsing(chestItemStack, EquipmentSlot.CHEST);

        return !wearingUsableElytra && !player.isFallFlying() && !player.isPassenger()
                && !player.isInWater() && !player.hasEffect(MobEffects.LEVITATION)
                && !player.getAbilities().instabuild && !player.getAbilities().flying;
    }
}

