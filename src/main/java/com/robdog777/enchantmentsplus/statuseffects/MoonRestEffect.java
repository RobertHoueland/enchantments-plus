package com.robdog777.enchantmentsplus.statuseffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class MoonRestEffect extends MobEffect {
    public MoonRestEffect() {
        super(
                MobEffectCategory.BENEFICIAL, // whether beneficial or harmful for entities
                0x464d43); // color in RGB
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the status effect every tick.
        return true;
    }
}
