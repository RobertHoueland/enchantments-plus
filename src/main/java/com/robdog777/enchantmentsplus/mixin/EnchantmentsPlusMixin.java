package com.robdog777.enchantmentsplus.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mixin(TitleScreen.class)
public class EnchantmentsPlusMixin {
    private static final Logger LOGGER = LogManager.getLogger("enchantmentsplus");

    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Enchantments plus is running");
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {}
}
