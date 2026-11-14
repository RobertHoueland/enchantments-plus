package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import com.robdog777.enchantmentsplus.enchants.ProjectileWeapon;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class ArrowDamageMixin implements ProjectileWeapon {
    @Unique
    private ItemStack enchantmentsplus$weapon = ItemStack.EMPTY;

    @Override
    public void enchantmentsplus$setWeapon(ItemStack weapon) {
        enchantmentsplus$weapon = weapon.copy();
    }

    @Inject(method = "applyEnchantmentEffects", at = @At("HEAD"))
    private void captureMobBow(LivingEntity user, float damageModifier, CallbackInfo ci) {
        ItemStack bow = user.getMainHandStack();
        if (!(bow.getItem() instanceof BowItem)) bow = user.getOffHandStack();
        if (bow.getItem() instanceof BowItem) enchantmentsplus$setWeapon(bow);
    }

    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean addImpactBonus(Entity target, DamageSource source, float damage) {
        PersistentProjectileEntity arrow = (PersistentProjectileEntity) (Object) this;
        if (!arrow.getWorld().isClient && arrow.getOwner() instanceof LivingEntity owner) {
            // This invocation is after vanilla velocity and critical scaling, before armor mitigation.
            damage += EnchantmentEffects.damageBonus(enchantmentsplus$weapon, owner, target);
        }
        return target.damage(source, damage);
    }

    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    private void applyFiringWeaponEffects(LivingEntity owner, Entity target) {
        EnchantmentHelper.onTargetDamaged(owner, target);
        EnchantmentEffects.postAttack(enchantmentsplus$weapon, owner, target);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void saveWeapon(NbtCompound nbt, CallbackInfo ci) {
        if (!enchantmentsplus$weapon.isEmpty()) nbt.put("EnchantmentsPlusWeapon", enchantmentsplus$weapon.writeNbt(new NbtCompound()));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void loadWeapon(NbtCompound nbt, CallbackInfo ci) {
        enchantmentsplus$weapon = ItemStack.fromNbt(nbt.getCompound("EnchantmentsPlusWeapon"));
    }
}
