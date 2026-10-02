package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @Inject(method = "getPossibleEntries", at = @At("RETURN"), cancellable = true)
    private static void removeDisabledEnchantments(int power, ItemStack stack,
                                                   Stream<RegistryEntry<Enchantment>> possibleEntries,
                                                   CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        // Vanilla removes conflicting candidates when selecting additional enchantments
        cir.getReturnValue().removeIf(entry -> !EnchantmentsPlus.isEnchantmentEnabled(entry.enchantment));
    }
}
