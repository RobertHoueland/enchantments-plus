package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @Inject(method = "getAvailableEnchantmentResults", at = @At("RETURN"))
    private static void removeDisabledEnchantments(int power, ItemStack stack,
                                                   Stream<Holder<Enchantment>> possibleEntries,
                                                   CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
        // Vanilla removes conflicting candidates when selecting additional enchantments
        cir.getReturnValue().removeIf(entry -> !EnchantmentsPlus.isEnchantmentEnabled(entry.enchantment()));
    }
}
