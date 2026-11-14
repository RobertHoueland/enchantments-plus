package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.config.EnchantmentSettings;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

        int level = EnchantmentSettings.enabled(EnchantmentsPlus.DUALLEAP)
                ? EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.DUALLEAP, player) : 0;
        jumpCount = Math.min(jumpCount, level);
        if (player.isOnGround() || player.isClimbing()) {
            jumpCount = level;
        } else if (!jumpedLastTick && jumpCount > 0 && player.getVelocity().y < 0
                && player.input.jumping && canJump(player)) {
            if (player.fallDistance < 4.0F) {
                jumpCount--;
                player.jump();
                player.playSound(EnchantmentsPlus.WhooshEvent, 0.7F, 1.0F);
            } else {
                player.playSound(EnchantmentsPlus.DenyEvent, 0.7F, 1.0F);
            }
        }

        jumpedLastTick = player.input.jumping;
    }

    @Unique
    private boolean canJump(ClientPlayerEntity player) {
        ItemStack chestItemStack = player.getEquippedStack(EquipmentSlot.CHEST);
        boolean wearingUsableElytra = chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack);

        return !wearingUsableElytra && !player.isFallFlying() && !player.hasVehicle()
                && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION)
                && !player.getAbilities().creativeMode && !player.getAbilities().flying;
    }
}
