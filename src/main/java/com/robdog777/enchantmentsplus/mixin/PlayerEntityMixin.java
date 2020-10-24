package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.NightVisionEnchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void tick(CallbackInfo ci) {
        Item helmet = this.getEquippedStack(EquipmentSlot.HEAD).getItem();

        if(helmet.hasNightVision) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                    1000000, 0));
        }
    }
}