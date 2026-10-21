package com.robdog777.enchantmentsplus.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record EnchantmentConfigPayload(long enabledMask) implements CustomPacketPayload {
    public static final Type<EnchantmentConfigPayload> ID =
            new Type<>(Identifier.fromNamespaceAndPath("enchantmentsplus", "enchantment_config"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentConfigPayload> CODEC = StreamCodec.ofMember(
            (payload, buf) -> buf.writeLong(payload.enabledMask),
            buf -> new EnchantmentConfigPayload(buf.readLong()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
