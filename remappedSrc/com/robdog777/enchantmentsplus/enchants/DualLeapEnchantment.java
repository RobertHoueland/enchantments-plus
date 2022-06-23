package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.Random;

public class DualLeapEnchantment extends Enchantment {
    public DualLeapEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinPower(int level) {
        return (20 * level);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return other != EnchantmentsPlus.MOONWALKER;
    }

    public String registryName() {
        return "dualleap";
    }

    private final static Random random = new Random();

    public static void play(PlayerEntity player) {
        World world = player.getEntityWorld();
        world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_TURTLE_SHAMBLE, SoundCategory.PLAYERS, 0.5f, 1);

        for (int i = 0; i < 5; i++) {
            double d = random.nextGaussian() * 0.02D;
            double e = random.nextGaussian() * 0.02D;
            double f = random.nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.CLOUD, player.getParticleX(1.0D), player.getY(), player.getParticleZ(1.0D), d, e, f);
        }
    }
}
