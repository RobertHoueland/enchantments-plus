package com.robdog777.enchantmentsplus.enchants;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public record EnchantmentEffects(String action) implements EnchantmentEntityEffect {
    public static final MapCodec<EnchantmentEffects> CODEC = Codec.STRING.fieldOf("action")
            .xmap(EnchantmentEffects::new, EnchantmentEffects::action);

    private static void strikeLightning(ServerLevel world, Entity target, int level, int chance, boolean enabled) {
        if (enabled && target.getRandom().nextInt(chance) < level && world.canSeeSky(target.blockPosition())) {
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(world, EntitySpawnReason.TRIGGERED);
            if (lightning != null) {
                lightning.snapTo(target.getX(), target.getY(), target.getZ());
                world.addFreshEntity(lightning);
            }
        }
    }

    private static void mysticMind(ServerLevel world, LivingEntity user, int level) {
        if (level <= 0 || user.getHealth() >= 6.0F || !EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.MYSTICMIND, world)) {
            return;
        }

        double startX = user.getX();
        double startY = user.getY();
        double startZ = user.getZ();

        for (int i = 0; i < 16; i++) {
            double x = user.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
            double y = Mth.clamp(user.getY() + user.getRandom().nextInt(16) - 8,
                    world.getMinY(), world.getMinY() + world.getLogicalHeight() - 1);
            double z = user.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
            if (user.isPassenger()) {
                user.stopRiding();
            }

            Vec3 oldPosition = user.position();
            if (user.randomTeleport(x, y, z, true)) {
                world.gameEvent(GameEvent.TELEPORT, oldPosition, GameEvent.Context.of(user));
                SoundEvent sound = user instanceof Fox
                        ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
                world.playSound(null, startX, startY, startZ, sound, SoundSource.PLAYERS, 1.0F, 1.0F);
                user.playSound(sound, 1.0F, 1.0F);
                break;
            }
        }
    }

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity target, Vec3 pos) {
        LivingEntity user = context.owner();

        switch (action) {
            case "cubical" -> {
                if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.CUBICAL, world)
                        && (target instanceof Creeper || target instanceof Slime)) {
                    target.hurtServer(world, world.damageSources().generic(), level * 5.0F);
                }
            }
            case "endslayer" -> {
                if (!EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.ENDSLAYER, world)) {
                    return;
                }
                if (target instanceof EnderDragon) {
                    target.hurtServer(world, world.damageSources().generic(), level * 10.0F);
                } else if (target instanceof EnderMan || target instanceof Endermite || target instanceof Shulker) {
                    target.hurtServer(world, world.damageSources().generic(), level * 5.0F);
                }
            }
            case "frostbite" -> {
                if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.FROSTBITE, world) && target instanceof LivingEntity livingTarget) {
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 40 * level, level - 1));
                }
            }
            case "levitation" -> {
                if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.LEVITATION, world) && target instanceof LivingEntity livingTarget) {
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20 * level, level - 1));
                }
            }
            case "lifesteal" -> {
                if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.LIFESTEAL, world) && user != null && user.getHealth() < 20.0F
                        && target instanceof LivingEntity livingTarget
                        && user.getRandom().nextFloat() < 0.10F + level * 0.20F
                        && livingTarget.getHealth() > 0.0F) {
                    world.playSound(null, user.blockPosition(), EnchantmentsPlus.BlurpEvent,
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                    user.heal(livingTarget.getHealth() * 0.5F);
                }
            }
            case "mysticmind" -> mysticMind(world, user, level);
            case "payback" -> {
                if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.PAYBACK, world) && user != null && user.getHealth() < 10.0F) {
                    target.hurtServer(world, world.damageSources().generic(), level * 0.5F * (20.0F - user.getHealth()));
                }
            }
            case "raider" -> {
                if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.RAIDER, world) &&
                        (target instanceof AbstractIllager || target instanceof Witch ||
                                target instanceof Vex || target instanceof Ravager)) {
                    target.hurtServer(world, world.damageSources().generic(), level * 5.0F);
                }
            }
            case "sniper" -> {
                if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.SNIPER, world)) {
                    if (user != null) {
                        float distance = user.distanceTo(target);
                        if (distance > 10.0F) {
                            target.hurtServer(world, world.damageSources().generic(), Math.min(40.0F, level * (distance / 2.5F)));
                        }
                    }
                }
            }
            case "stormstrike" -> strikeLightning(world, target, level, 20,
                    EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.STORMSTRIKE, world));
            case "thunderlord" -> strikeLightning(world, target, level, 10,
                    EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.THUNDERLORD, world));
            case "toxicstrike" -> {
                if (EnchantmentsPlus.isEnchantmentEnabled(EnchantmentsPlus.TOXICSTRIKE, world) && target instanceof LivingEntity livingTarget) {
                    livingTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 40 * level, level - 1));
                }
            }
            default -> EnchantmentsPlus.LOGGER.warn("Unknown enchantment effect action: {}", action);
        }
    }

    @Override
    public MapCodec<EnchantmentEffects> codec() {
        return CODEC;
    }
}
