package com.robdog777.enchantmentsplus.mixin;

import com.mojang.datafixers.util.Either;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceManagerRegistryLoadTask;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Optional;

@Mixin(ResourceManagerRegistryLoadTask.class)
public abstract class RegistryLoaderMixin {
    // Only change server resource loading, not network/cached-resource decoding on clients.
    @ModifyArgs(method = "lambda$load$2", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/resources/RegistryLoadTask$PendingRegistration;<init>(" +
                    "Lnet/minecraft/resources/ResourceKey;Lcom/mojang/datafixers/util/Either;" +
                    "Lnet/minecraft/core/RegistrationInfo;)V"))
    private void applyConfiguredMaxLevel(Args args) {
        ResourceKey<?> key = args.get(0);
        int maxLevel = EnchantmentsPlus.getConfiguredMaxLevel(key);
        if (maxLevel < 0) {
            return;
        }

        Either<Enchantment, Exception> result = args.get(1);
        args.set(1, result.mapLeft(enchantment -> {
            Enchantment.EnchantmentDefinition definition = enchantment.definition();
            Enchantment.EnchantmentDefinition configured = new Enchantment.EnchantmentDefinition(
                    definition.supportedItems(), definition.primaryItems(), definition.weight(), maxLevel,
                    definition.minCost(), definition.maxCost(), definition.anvilCost(), definition.slots());
            return new Enchantment(enchantment.description(), configured, enchantment.exclusiveSet(), enchantment.effects());
        }));

        // Modified entries must be sent in full rather than resolved from the client's vanilla pack.
        RegistrationInfo info = args.get(2);
        args.set(2, new RegistrationInfo(Optional.empty(), info.lifecycle()));
    }
}
