package com.robdog777.enchantmentsplus.event;

import com.robdog777.enchantmentsplus.networking.ModMessages;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY = "key.category.enchantmentsplus";
    public static final String KEY_MOONWALK = "key.enchantmentsplus.moonwalk";

    public static KeyBinding moonWalk;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (moonWalk.wasPressed()) {
                ClientPlayNetworking.send(ModMessages.MOONWALK_ID, PacketByteBufs.create());
            }
        });
    }

    public static void register() {
        moonWalk = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_MOONWALK,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
