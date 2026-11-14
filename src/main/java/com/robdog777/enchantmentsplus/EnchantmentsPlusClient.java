package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.config.EnchantmentSettings;
import com.robdog777.enchantmentsplus.network.EnchantmentConfigNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class EnchantmentsPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.INIT.register((handler, client) -> EnchantmentSettings.clear());
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> EnchantmentSettings.clear());
        ClientPlayNetworking.registerGlobalReceiver(EnchantmentConfigNetworking.CHANNEL, (client, handler, buffer, sender) -> {
            long mask = buffer.readLong();
            int[] levels = new int[EnchantmentsPlus.ENCHANTMENTS.length];
            for (int i = 0; i < levels.length; i++) levels[i] = Math.max(1, Math.min(99, buffer.readVarInt()));
            var settings = new EnchantmentSettings.Snapshot(mask, levels);
            client.execute(() -> {
                if (client.getNetworkHandler() == handler) EnchantmentSettings.sync(settings);
            });
        });
    }
}
