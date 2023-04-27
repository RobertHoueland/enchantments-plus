package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.robdog777.enchantmentsplus.enchants.BlazeWalkerEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType entityType, World world) {
        super(entityType, world);
    }

    // blazewalker
    @Inject(method = "applyMovementEffects", at = @At("HEAD"))
    protected void applyMovementEffects(BlockPos pos, CallbackInfo ci) {
        LivingEntity casted = (LivingEntity) (Object) this;
        int j = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.BLAZEWALKER, casted);
        if (j > 0) {
            BlazeWalkerEnchantment.freezeLava(casted, this.world, pos, j);
        }
    }
}