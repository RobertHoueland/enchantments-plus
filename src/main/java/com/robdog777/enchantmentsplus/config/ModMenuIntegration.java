package com.robdog777.enchantmentsplus.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (!FabricLoader.getInstance().isModLoaded("cloth-config")) {
            return parent -> null;
        }
        return ClothConfigScreen.getFactory();
    }

    // Keep Cloth Config types out of the Mod Menu entrypoint's executable code
    private static final class ClothConfigScreen {
        private static boolean noticeTransformerRegistered;

        private static ConfigScreenFactory<?> getFactory() {
            return parent -> {
                registerNoticeTransformer();
                return AutoConfig.getConfigScreen(EnchantmentsPlusConfig.class, parent).get();
            };
        }

        private static void registerNoticeTransformer() {
            if (noticeTransformerRegistered) {
                return;
            }

            AutoConfig.getGuiRegistry(EnchantmentsPlusConfig.class).registerPredicateTransformer(
                    (entries, optionName, field, config, defaults, registry) -> {
                        var transformedEntries = new ArrayList<>(entries);
                        transformedEntries.add(0, ConfigEntryBuilder.create()
                                .startTextDescription(Text.translatable(
                                        "text.autoconfig.enchantmentsplus.client_only_notice"))
                                .build());
                        return transformedEntries;
                    },
                    field -> field.getName().equals("enableBlazeWalker"));
            noticeTransformerRegistered = true;
        }
    }
}
