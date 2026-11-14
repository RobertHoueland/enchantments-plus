package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.ConfigurableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentLevelEntry.class)
public abstract class EnchantmentWeightMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment$Rarity;getWeight()I"))
    private static int customWeight(Enchantment.Rarity rarity, Enchantment enchantment, int level) {
        return enchantment instanceof ConfigurableEnchantment custom ? custom.weight() : rarity.getWeight();
    }
}
