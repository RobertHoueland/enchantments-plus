package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.ProjectileWeapon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BowItem.class)
public abstract class BowItemMixin {
    @Redirect(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArrowItem;createArrow(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/LivingEntity;)Lnet/minecraft/entity/projectile/PersistentProjectileEntity;"))
    private PersistentProjectileEntity captureFiringWeapon(ArrowItem item, World world, ItemStack ammunition, LivingEntity shooter,
                                                          ItemStack bow, World useWorld, LivingEntity user, int remainingUseTicks) {
        PersistentProjectileEntity arrow = item.createArrow(world, ammunition, shooter);
        // Capture before durability loss, including the bow's final shot.
        ((ProjectileWeapon) arrow).enchantmentsplus$setWeapon(bow);
        return arrow;
    }
}
