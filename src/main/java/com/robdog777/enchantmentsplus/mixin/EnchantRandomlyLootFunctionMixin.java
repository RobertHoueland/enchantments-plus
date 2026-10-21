package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;

@Mixin(EnchantRandomlyFunction.class)
public abstract class EnchantRandomlyLootFunctionMixin {
    @ModifyArg(method = "run", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/util/Util;getRandomSafe(Ljava/util/List;Lnet/minecraft/util/RandomSource;)Ljava/util/Optional;"), index = 0)
    private List<Holder<Enchantment>> removeDisabledEnchantments(List<Holder<Enchantment>> entries) {
        // Keep the loot function's existing tag and item-compatibility restrictions
        return entries.stream().filter(EnchantmentsPlus::isEnchantmentEnabled).toList();
    }
}
