package com.robdog777.enchantmentsplus.networking.packet;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class MoonwalkC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // happens only on server
        int moonrestCooldown = 400; // 20 seconds
        ItemStack itemStackFeet = player.getEquippedStack(EquipmentSlot.FEET);
        int moonwalkerLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.MOONWALKER, itemStackFeet);

        // moonwalk boost
        if (moonwalkerLevel > 0) {
            // moonrest cooldown
            if (!player.hasStatusEffect(EnchantmentsPlus.MOONREST)) {
                player.world.playSound(null, player.getBlockPos(), EnchantmentsPlus.SwoopEvent, player.getSoundCategory(), 0.7f, 1f);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,
                        moonwalkerLevel * 100, moonwalkerLevel - 1, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,
                        moonwalkerLevel * 100, 0, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(EnchantmentsPlus.MOONREST,
                        moonrestCooldown, 0, false, false, true));
            }
        }
    }
}
