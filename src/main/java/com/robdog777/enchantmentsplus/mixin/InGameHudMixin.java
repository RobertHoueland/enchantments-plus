package com.robdog777.enchantmentsplus.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    boolean renderFullness = false;

    /*@Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F"))
    private float setBarProgress(ClientPlayerEntity player, float baseTime) {
        float progress = EnchantmentsPlus.getProgress(player.getAttackCooldownProgress(baseTime));

        if (progress == 2.0F)
            renderFullness = true;

        return progress == 2.0F ? 1.0F : progress;

    }

    @ModifyVariable(method = "renderCrosshair", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;targetedEntity:Lnet/minecraft/entity/Entity;"), index = 5)
    private boolean showPlus(boolean _original) {
        boolean render = renderFullness;
        renderFullness = false;
        return render;
    }

    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F"))
    private float setHotBarProgress(ClientPlayerEntity player, float baseTime) {
        float progress = EnchantmentsPlus.getProgress(player.getAttackCooldownProgress(baseTime));
        return progress == 2.0F ? 0.99F : progress;
    }*/
}