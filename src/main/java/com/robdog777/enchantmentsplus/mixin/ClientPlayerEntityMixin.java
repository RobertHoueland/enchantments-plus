package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    private int jumpCount = 0;
    private boolean jumpedLastTick = false;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;

        if (player.isOnGround() || player.isClimbing()) {
            jumpCount = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.DUALLEAP, player);
        } else if (!jumpedLastTick && jumpCount > 0 && player.getVelocity().y < 0) {
            // 200ms delay on double taps
            if (player.input.jumping && canJump(player)) {
                jumpCount--;
                player.jump();
            }
        }

        jumpedLastTick = player.input.jumping;
    }

    private boolean canJump(ClientPlayerEntity player) {
        ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
        boolean wearingUsableElytra = chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack);

        return !wearingUsableElytra && !player.isFallFlying() && !player.hasVehicle()
                && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION)
                && !player.getAbilities().creativeMode && !player.getAbilities().flying;
    }
}
