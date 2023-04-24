package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.event.KeyInputHandler;
import com.robdog777.enchantmentsplus.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;

public class EnchantmentsPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModMessages.registerC2SPackets();
        ModMessages.registerS2CPackets();
    }
}
