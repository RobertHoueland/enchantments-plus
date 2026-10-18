package com.robdog777.enchantmentsplus.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record EnchantmentConfigPayload(long enabledMask) implements CustomPayload {
    public static final Id<EnchantmentConfigPayload> ID =
            new Id<>(Identifier.of("enchantmentsplus", "enchantment_config"));
    public static final PacketCodec<RegistryByteBuf, EnchantmentConfigPayload> CODEC = PacketCodec.of(
            (payload, buf) -> buf.writeLong(payload.enabledMask),
            buf -> new EnchantmentConfigPayload(buf.readLong()));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
