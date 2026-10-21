package com.robdog777.enchantmentsplus.mixin;

import com.mojang.serialization.Decoder;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryLoader;
import net.minecraft.registry.entry.RegistryEntryInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Optional;

@Mixin(RegistryLoader.class)
public abstract class RegistryLoaderMixin {
    // Only wrap server resource loading, not the client's network/cached-resource decoding.
    @ModifyArgs(method = "loadFromResource(Lnet/minecraft/resource/ResourceManager;" +
            "Lnet/minecraft/registry/RegistryOps$RegistryInfoGetter;Lnet/minecraft/registry/MutableRegistry;" +
            "Lcom/mojang/serialization/Decoder;Ljava/util/Map;)V", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/registry/RegistryLoader;parseAndAdd(Lnet/minecraft/registry/MutableRegistry;" +
                    "Lcom/mojang/serialization/Decoder;Lnet/minecraft/registry/RegistryOps;" +
                    "Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/resource/Resource;" +
                    "Lnet/minecraft/registry/entry/RegistryEntryInfo;)V"))
    private static void applyConfiguredMaxLevel(Args args) {
        RegistryKey<?> key = args.get(3);
        int maxLevel = EnchantmentsPlus.getConfiguredMaxLevel(key);
        if (maxLevel < 0) {
            return;
        }

        Decoder<Enchantment> decoder = args.get(1);
        args.set(1, decoder.map(enchantment -> {
            Enchantment.Definition definition = enchantment.definition();
            Enchantment.Definition configured = new Enchantment.Definition(
                    definition.supportedItems(), definition.primaryItems(), definition.weight(), maxLevel,
                    definition.minCost(), definition.maxCost(), definition.anvilCost(), definition.slots());
            return new Enchantment(enchantment.description(), configured, enchantment.exclusiveSet(), enchantment.effects());
        }));

        // Modified entries must be sent in full
        RegistryEntryInfo info = args.get(5);
        args.set(5, new RegistryEntryInfo(Optional.empty(), info.lifecycle()));
    }
}
