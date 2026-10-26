package com.robdog777.enchantmentsplus.network;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class EnchantmentConfigNetworking {
    private static Long lastBroadcastMask;

    private EnchantmentConfigNetworking() {
    }

    public static void initialize() {
        PayloadTypeRegistry.clientboundPlay().register(EnchantmentConfigPayload.ID, EnchantmentConfigPayload.CODEC);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (ServerPlayNetworking.canSend(handler, EnchantmentConfigPayload.ID)) {
                sender.sendPacket(new EnchantmentConfigPayload(EnchantmentsPlus.getEnabledEnchantmentMask()));
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            long currentMask = EnchantmentsPlus.getEnabledEnchantmentMask();
            if (lastBroadcastMask != null && lastBroadcastMask == currentMask) {
                return;
            }
            lastBroadcastMask = currentMask;
            
            server.getPlayerList().getPlayers().forEach(player -> {
                if (ServerPlayNetworking.canSend(player, EnchantmentConfigPayload.ID)) {
                    ServerPlayNetworking.send(player, new EnchantmentConfigPayload(currentMask));
                }
            });
        });
    }
}
