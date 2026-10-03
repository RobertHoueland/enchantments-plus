package com.robdog777.enchantmentsplus.enchants;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

public record EnchantmentEffects(String action) implements EnchantmentEntityEffect {
    public static final MapCodec<EnchantmentEffects> CODEC = Codec.STRING.fieldOf("action")
            .xmap(EnchantmentEffects::new, EnchantmentEffects::action);

    private static void strikeLightning(ServerWorld world, Entity target, int level, int chance, boolean enabled) {
        if (enabled && target.getRandom().nextInt(chance) < level && world.isSkyVisible(target.getBlockPos())) {
            LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world, SpawnReason.TRIGGERED);
            if (lightning != null) {
                lightning.refreshPositionAfterTeleport(target.getX(), target.getY(), target.getZ());
                world.spawnEntity(lightning);
            }
        }
    }

    private static void mysticMind(ServerWorld world, LivingEntity user, int level) {
        if (level <= 0 || user.getHealth() >= 6.0F || !EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableMysticMind) {
            return;
        }

        double startX = user.getX();
        double startY = user.getY();
        double startZ = user.getZ();

        for (int i = 0; i < 16; i++) {
            double x = user.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
            double y = MathHelper.clamp(user.getY() + user.getRandom().nextInt(16) - 8,
                    world.getBottomY(), world.getBottomY() + world.getLogicalHeight() - 1);
            double z = user.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
            if (user.hasVehicle()) {
                user.stopRiding();
            }

            Vec3d oldPosition = user.getPos();
            if (user.teleport(x, y, z, true)) {
                world.emitGameEvent(GameEvent.TELEPORT, oldPosition, GameEvent.Emitter.of(user));
                SoundEvent sound = user instanceof FoxEntity
                        ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                world.playSound(null, startX, startY, startZ, sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
                user.playSound(sound, 1.0F, 1.0F);
                break;
            }
        }
    }

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        LivingEntity user = context.owner();

        switch (action) {
            case "cubical" -> {
                if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableCubical
                        && (target instanceof CreeperEntity || target instanceof SlimeEntity)) {
                    target.damage(world, world.getDamageSources().generic(), level * 5.0F);
                }
            }
            case "endslayer" -> {
                if (!EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableEndSlayer) {
                    return;
                }
                if (target instanceof EnderDragonEntity) {
                    target.damage(world, world.getDamageSources().generic(), level * 10.0F);
                } else if (target instanceof EndermanEntity || target instanceof EndermiteEntity || target instanceof ShulkerEntity) {
                    target.damage(world, world.getDamageSources().generic(), level * 5.0F);
                }
            }
            case "frostbite" -> {
                if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableFrostbite && target instanceof LivingEntity livingTarget) {
                    livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40 * level, level - 1));
                }
            }
            case "levitation" -> {
                if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLevitation && target instanceof LivingEntity livingTarget) {
                    livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20 * level, level - 1));
                }
            }
            case "lifesteal" -> {
                if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLifeSteal && user != null && user.getHealth() < 20.0F
                        && target instanceof LivingEntity livingTarget
                        && user.getRandom().nextFloat() < 0.10F + level * 0.20F
                        && livingTarget.getHealth() > 0.0F) {
                    world.playSound(null, user.getBlockPos(), EnchantmentsPlus.BlurpEvent,
                            SoundCategory.PLAYERS, 1.0F, 1.0F);
                    user.heal(livingTarget.getHealth() * 0.5F);
                }
            }
            case "mysticmind" -> mysticMind(world, user, level);
            case "payback" -> {
                if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enablePayback && user != null && user.getHealth() < 10.0F) {
                    target.damage(world, world.getDamageSources().generic(), level * 0.5F * (20.0F - user.getHealth()));
                }
            }
            case "raider" -> {
                if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableRaider && target instanceof LivingEntity
                        && (target instanceof IllagerEntity || target instanceof WitchEntity
                        || target instanceof VexEntity || target instanceof RavagerEntity)) {
                    target.damage(world, world.getDamageSources().generic(), level * 5.0F);
                }
            }
            case "sniper" -> {
                if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableSniper) {
                    if (user != null) {
                        float distance = user.distanceTo(target);
                        if (distance > 10.0F) {
                            target.damage(world, world.getDamageSources().generic(), Math.min(40.0F, level * (distance / 2.5F)));
                        }
                    }
                }
            }
            case "stormstrike" -> strikeLightning(world, target, level, 20,
                    EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableStormStrike);
            case "thunderlord" -> strikeLightning(world, target, level, 10,
                    EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableThunderlord);
            case "toxicstrike" -> {
                if (EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableToxicStrike && target instanceof LivingEntity livingTarget) {
                    livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40 * level, level - 1));
                }
            }
            default -> EnchantmentsPlus.LOGGER.warn("Unknown enchantment effect action: {}", action);
        }
    }

    @Override
    public MapCodec<EnchantmentEffects> getCodec() {
        return CODEC;
    }
}
