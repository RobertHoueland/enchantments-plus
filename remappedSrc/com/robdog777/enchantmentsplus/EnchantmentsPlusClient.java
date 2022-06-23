package com.robdog777.enchantmentsplus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class EnchantmentsPlusClient implements ClientModInitializer {
    public static boolean pressed = false;
    KeyBinding moonWalk = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.enchantmentsplus.moonwalk", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_R, // The keycode of the key
            "category.enchantmentsplus.keys" // The translation key of the keybinding's category.
    ));

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            pressed = moonWalk.isPressed();
        });
    }
}
