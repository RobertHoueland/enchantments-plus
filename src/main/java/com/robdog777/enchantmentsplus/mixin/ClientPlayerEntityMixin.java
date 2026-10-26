package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentLookup;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
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

        // Dual leap
        int level = EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.DUALLEAP, player.level())
                ? EnchantmentLookup.getEquipmentLevel(EnchantmentsPlus.DUALLEAP, player) : 0;
        jumpCount = Math.min(jumpCount, level);
        if (player.onGround() || player.onClimbable()) {
            jumpCount = level;
        } else if (!jumpedLastTick && player.input.keyPresses.jump() && jumpCount > 0
                && player.getDeltaMovement().y < 0 && canJump(player)) {
            // don't let the player use the leap after a huge fall or else they can effectively avoid fall damage
            if (player.fallDistance < 4.0f) {
                jumpCount--;
                player.jumpFromGround();

                player.playSound(EnchantmentsPlus.WhooshEvent, 0.7f, 1f);
            } else if (player.fallDistance >= 4.0f) {
                // player had fallen too many blocks, this prevents negating fall damage
                player.playSound(EnchantmentsPlus.DenyEvent, 0.7f, 1f);
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

