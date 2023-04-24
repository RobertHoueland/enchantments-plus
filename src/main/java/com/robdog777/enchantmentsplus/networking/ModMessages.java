package com.robdog777.enchantmentsplus.networking;

import com.robdog777.enchantmentsplus.networking.packet.MoonwalkC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier MOONWALK_ID = new Identifier("enchantmentsplus", "moonwalk");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(MOONWALK_ID, MoonwalkC2SPacket::receive);
    }

    public static void registerS2CPackets() {
    }
}
