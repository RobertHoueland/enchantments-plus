package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.EnchantmentEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEntity.class)
public abstract class MobDamageMixin {
    @Redirect(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"))
    private float originalHitBonus(ItemStack stack, EntityGroup group, Entity target) {
        return EnchantmentHelper.getAttackDamage(stack, group)
                + EnchantmentEffects.damageBonus(stack, (MobEntity) (Object) this, target);
    }
}
