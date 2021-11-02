package com.robdog777.enchantmentsplus.mixin;

import com.robdog777.enchantmentsplus.enchants.NightVisionEnchantment;
import com.robdog777.enchantmentsplus.enchants.BlazeWalkerEnchantment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    private Object ItemStackHelper;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void tick(CallbackInfo ci) {
        Item stack = this.getEquippedStack(EquipmentSlot.HEAD).getItem();

        /*if(EnchantmentHelper.getLevel(Enchantment NightVisionEnchantment, ItemStack stack)) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,
                    1000000, 0));
        }*/
    }

    protected void applyMovementEffects(BlockPos pos, CallbackInfo ci) {
        /*LivingEntity casted = (LivingEntity) (Object) this;
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(BlazeWalkerEnchantment, casted);

        if (entry != null) {
            ItemStack item = (ItemStack) ((Map.Entry<?, ?>) entry).getValue();
            BlazeWalkerEnchantment enchantment = ((BlazeWalkerEnchantment) (BlazeWalkerEnchantment));
            enchantment.freezeLava(casted, this.world, pos, level, item), item);
        }*/
    }
}