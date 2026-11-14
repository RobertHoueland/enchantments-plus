package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.config.EnchantmentSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantRandomlyLootFunction.class)
public abstract class EnchantRandomlyLootFunctionMixin {
    @Shadow
    @Final
    private List<Enchantment> enchantments;

    @Shadow
    private static ItemStack addEnchantmentToStack(ItemStack stack, Enchantment enchantment, Random random) {
        throw new AssertionError();
    }

    @Inject(method = "process", at = @At("HEAD"), cancellable = true)
    private void filterExplicitChoices(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> cir) {
        // An empty vanilla list means all random-selectable enchantments, already gated by our subclass.
        if (enchantments.isEmpty()) return;
        List<Enchantment> enabled = enchantments.stream().filter(EnchantmentSettings::enabled).toList();
        if (enabled.size() == enchantments.size()) return;
        cir.setReturnValue(enabled.isEmpty() ? stack
                : addEnchantmentToStack(stack, enabled.get(context.getRandom().nextInt(enabled.size())), context.getRandom()));
    }
}
