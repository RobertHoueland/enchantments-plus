package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.network.EnchantmentConfigPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class EnchantmentsPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(EnchantmentConfigPayload.ID, (payload, context) ->
                EnchantmentsPlus.setSyncedEnabledEnchantmentMask(payload.enabledMask()));
        ClientPlayConnectionEvents.INIT.register((handler, client) ->
                EnchantmentsPlus.clearSyncedEnabledEnchantmentMask());
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
                EnchantmentsPlus.clearSyncedEnabledEnchantmentMask());
    }
}
