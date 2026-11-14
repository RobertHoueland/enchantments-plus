package com.robdog777.enchantmentsplus.config;

import com.robdog777.enchantmentsplus.EnchantmentsPlus;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> EnchantmentsPlus.CLOTH_CONFIG_LOADED ? EnchantmentsPlusConfigScreen.create(parent) : null;
    }
}
