package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(TradeOffers.EnchantBookFactory.class)
public abstract class EnchantBookFactoryMixin {
    @Redirect(method = "create", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/registry/Registry;getRandomEntry(Lnet/minecraft/registry/tag/TagKey;" +
                    "Lnet/minecraft/util/math/random/Random;)Ljava/util/Optional;"))
    private Optional<RegistryEntry<Enchantment>> selectEnabledEnchantment(Registry<Enchantment> registry,
                                                                         TagKey<Enchantment> tag, Random random) {
        // Filter before selecting so disabled entries do not replace valid book offers
        return registry.getOptional(tag).flatMap(entries -> Util.getRandomOrEmpty(entries.stream()
                .filter(EnchantmentsPlus::isEnchantmentEnabled)
                .toList(), random));
    }
}
