package com.robdog777.enchantmentsplus.mixin;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ItemStack itemStackHead = this.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack itemStackFeet = this.getEquippedStack(EquipmentSlot.FEET);
        ItemStack itemStackHand = this.getEquippedStack(EquipmentSlot.MAINHAND);

        // Lunar Sight
        int nightVisionLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.LUNARSIGHT, itemStackHead);
        if (nightVisionLevel > 0 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableLunarSight) {
            // Stays for 11 seconds, otherwise sky flashes at <=10 seconds
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                    220, 0, false, false, true));
        }

        // Hiker
        int hikerLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.HIKER, itemStackFeet);
        if (hikerLevel > 0 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableHiker) {
            this.setStepHeight(hikerLevel + 0.1F);
        } else {
            this.setStepHeight(0.6F);
        }

        // Excavator
        int excavatorLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.EXCAVATOR, itemStackHand);
        setRange(excavatorLevel > 0, excavatorLevel);

        // Moonwalker
        int moonWalkerLevel = EnchantmentHelper.getLevel(EnchantmentsPlus.MOONWALKER, itemStackFeet);
        if (moonWalkerLevel > 0 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableMoonWalker) {
            // constant effects
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20,
                    0, false, false, true));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20,
                    0, false, false, true));

            // cooldown for stronger effect
            if (!this.hasStatusEffect(EnchantmentsPlus.MOONREST)) {
                this.getWorld().playSound(null, this.getBlockPos(), EnchantmentsPlus.SwoopEvent,
                        this.getSoundCategory(), 1.0f, 1f);
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, moonWalkerLevel * 100,
                        moonWalkerLevel + 1, false, false, true));
                // 20 seconds
                int moonRestCooldown = 400;
                this.addStatusEffect(new StatusEffectInstance(EnchantmentsPlus.MOONREST,
                        moonRestCooldown, 0, false, false, true));
            }
        }
    }

    @Unique
    public void setRange(boolean change_range, int level) {
        if (change_range && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableExcavator) {
            Objects.requireNonNull(this.getAttributeInstance(ReachEntityAttributes.REACH)).setBaseValue(level);
            Objects.requireNonNull(this.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE)).setBaseValue(level);
        } else {
            Objects.requireNonNull(this.getAttributeInstance(ReachEntityAttributes.REACH)).setBaseValue(0.0);
            Objects.requireNonNull(this.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE)).setBaseValue(0.0);
        }
    }
}