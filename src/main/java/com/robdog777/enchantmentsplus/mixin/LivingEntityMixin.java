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

    @Inject(method = "applyMovementEffects", at = @At("HEAD"))
    protected void applyMovementEffects(BlockPos pos, CallbackInfo ci) {
        // blazewalker
        LivingEntity casted = (LivingEntity) (Object) this;
        int blazeWalkerLevel = EnchantmentHelper.getEquipmentLevel(EnchantmentsPlus.BLAZEWALKER, casted);
        if (blazeWalkerLevel > 0 && EnchantmentsPlus.CONFIG_HOLDER.getConfig().enableBlazeWalker) {
            BlazeWalkerEnchantment.freezeLava(casted, this.getWorld(), pos, blazeWalkerLevel);
        }
    }
}