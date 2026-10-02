package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(EnchantRandomlyLootFunction.class)
public abstract class EnchantRandomlyLootFunctionMixin {
    @ModifyArg(method = "process", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/util/Util;getRandomOrEmpty(Ljava/util/List;" +
                    "Lnet/minecraft/util/math/random/Random;)Ljava/util/Optional;"), index = 0)
    private List<RegistryEntry<Enchantment>> removeDisabledEnchantments(List<RegistryEntry<Enchantment>> entries) {
        // Keep the loot function's existing tag and item-compatibility restrictions
        return entries.stream().filter(EnchantmentsPlus::isEnchantmentEnabled).toList();
    }
}
