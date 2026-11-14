package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.ConfigurableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilCostMixin {
    @Unique
    private int enchantmentsplus$anvilCost;

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getRarity()Lnet/minecraft/enchantment/Enchantment$Rarity;"))
    private Enchantment.Rarity rememberCost(Enchantment enchantment) {
        enchantmentsplus$anvilCost = enchantment instanceof ConfigurableEnchantment custom ? custom.anvilCost() : 0;
        return enchantment.getRarity();
    }

    // 1.20.1 slot 17 is the rarity cost. Change only the switch assignments, BEFORE the book discount.
    @ModifyVariable(method = "updateResult", at = @At("STORE"), index = 17,
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getRarity()Lnet/minecraft/enchantment/Enchantment$Rarity;"),
                    to = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I", ordinal = 1)))
    private int customAnvilCost(int vanilla) {
        return enchantmentsplus$anvilCost > 0 ? enchantmentsplus$anvilCost : vanilla;
    }
}
