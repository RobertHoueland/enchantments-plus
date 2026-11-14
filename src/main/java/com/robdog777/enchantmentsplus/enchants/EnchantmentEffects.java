package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.config.EnchantmentSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

public final class EnchantmentEffects {
    private EnchantmentEffects() { }

    public static float damageBonus(ItemStack weapon, LivingEntity user, Entity target) {
        float bonus = 0;
        for (Enchantment enchantment : new Enchantment[]{EnchantmentsPlus.CUBICAL, EnchantmentsPlus.ENDSLAYER,
                EnchantmentsPlus.PAYBACK, EnchantmentsPlus.RAIDER, EnchantmentsPlus.SNIPER}) {
            int level = EnchantmentHelper.getLevel(enchantment, weapon);
            if (level <= 0 || !EnchantmentSettings.enabled(enchantment)) continue;
            bonus += switch (((ConfigurableEnchantment) enchantment).id()) {
                case "cubical" -> target instanceof CreeperEntity || target instanceof SlimeEntity ? level * 5.0F : 0;
                case "endslayer" -> target instanceof EnderDragonEntity || target instanceof EnderDragonPart ? level * 10.0F
                        : target instanceof EndermanEntity || target instanceof EndermiteEntity || target instanceof ShulkerEntity ? level * 5.0F : 0;
                case "payback" -> user.getHealth() < 10.0F ? level * 0.5F * (20.0F - user.getHealth()) : 0;
                case "raider" -> target instanceof IllagerEntity || target instanceof WitchEntity
                        || target instanceof VexEntity || target instanceof RavagerEntity ? level * 5.0F : 0;
                case "sniper" -> user.distanceTo(target) > 10.0F ? Math.min(40.0F, level * user.distanceTo(target) / 2.5F) : 0;
                default -> 0;
            };
        }
        return bonus;
    }

    public static void postAttack(ItemStack weapon, LivingEntity user, Entity target) {
        EnchantmentHelper.fromNbt(weapon.getEnchantments()).forEach((enchantment, level) -> {
            if (enchantment instanceof ConfigurableEnchantment custom && !custom.id().equals("mysticmind")) {
                apply(custom, user, target, level);
            }
        });
    }

    public static void apply(ConfigurableEnchantment enchantment, LivingEntity user, Entity target, int level) {
        if (level <= 0 || !(user.getWorld() instanceof ServerWorld world) || !EnchantmentSettings.enabled(enchantment)) return;
        switch (enchantment.id()) {
            // Damage bonuses are added to the original hit by the combat mixins, never as a second hit.
            case "frostbite" -> {
                if (target instanceof LivingEntity living) living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40 * level, level - 1));
            }
            case "levitation" -> {
                if (target instanceof LivingEntity living) living.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20 * level, level - 1));
            }
            case "toxicstrike" -> {
                if (target instanceof LivingEntity living) living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40 * level, level - 1));
            }
            case "lifesteal" -> {
                if (user.getHealth() < 20.0F && target instanceof LivingEntity living
                        && user.getRandom().nextFloat() < 0.10F + level * 0.20F && living.getHealth() > 0) {
                    world.playSound(null, user.getBlockPos(), EnchantmentsPlus.BlurpEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    user.heal(living.getHealth() * 0.5F);
                }
            }
            case "mysticmind" -> mysticMind(world, user);
            case "stormstrike", "thunderlord" -> {
                int chance = enchantment.id().equals("stormstrike") ? 20 : 10;
                if (user.getRandom().nextInt(chance) < level && world.isSkyVisible(target.getBlockPos())) {
                    LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                    if (lightning != null) {
                        lightning.refreshPositionAfterTeleport(target.getPos());
                        world.spawnEntity(lightning);
                    }
                }
            }
            default -> { }
        }
    }

    private static void mysticMind(ServerWorld world, LivingEntity user) {
        if (user.getHealth() >= 6.0F) return;
        double startX = user.getX(), startY = user.getY(), startZ = user.getZ();
        for (int i = 0; i < 16; i++) {
            double x = user.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
            double y = MathHelper.clamp(user.getY() + user.getRandom().nextInt(16) - 8,
                    world.getBottomY(), world.getBottomY() + world.getLogicalHeight() - 1);
            double z = user.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
            if (user.hasVehicle()) user.stopRiding();
            Vec3d oldPosition = user.getPos();
            if (user.teleport(x, y, z, true)) {
                world.emitGameEvent(GameEvent.TELEPORT, oldPosition, GameEvent.Emitter.of(user));
                var sound = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                world.playSound(null, startX, startY, startZ, sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
                user.playSound(sound, 1.0F, 1.0F);
                break;
            }
        }
    }
}
