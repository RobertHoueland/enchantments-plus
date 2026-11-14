package com.robdog777.enchantmentsplus.network;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.config.EnchantmentSettings;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public final class EnchantmentConfigNetworking {
    public static final Identifier CHANNEL = new Identifier(EnchantmentsPlus.MOD_ID, "enchantment_settings");
    private static EnchantmentSettings.Snapshot lastSettings;

    private EnchantmentConfigNetworking() { }

    public static void register() {
        ServerLifecycleEvents.SERVER_STARTING.register(EnchantmentSettings::setServer);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            EnchantmentSettings.setServer(null);
            lastSettings = null;
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> send(handler.player, EnchantmentSettings.localSnapshot()));
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 20 != 0) return;
            var settings = EnchantmentSettings.localSnapshot();
            if (lastSettings == null || settings.enabledMask() != lastSettings.enabledMask()
                    || !Arrays.equals(settings.maxLevels(), lastSettings.maxLevels())) {
                lastSettings = settings;
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) send(player, settings);
            }
        });
    }

    private static void send(ServerPlayerEntity player, EnchantmentSettings.Snapshot settings) {
        if (!ServerPlayNetworking.canSend(player, CHANNEL)) return;
        var buffer = PacketByteBufs.create();
        buffer.writeLong(settings.enabledMask());
        for (int level : settings.maxLevels()) buffer.writeVarInt(level);
        ServerPlayNetworking.send(player, CHANNEL, buffer);
    }
}
