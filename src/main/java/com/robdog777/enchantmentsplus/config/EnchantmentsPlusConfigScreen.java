package com.robdog777.enchantmentsplus.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.Requirement;
import me.shedaniel.clothconfig2.gui.ClothConfigScreen;
import me.shedaniel.clothconfig2.gui.entries.SubCategoryListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public final class EnchantmentsPlusConfigScreen {
    private static final String PREFIX = "text.autoconfig.enchantmentsplus.";

    private EnchantmentsPlusConfigScreen() {
    }

    public static Screen create(Screen parent) {
        var holder = ClothConfigIntegration.getConfigHolder();
        EnchantmentsPlusConfig config = holder.getConfig();
        EnchantmentsPlusConfig defaults = new EnchantmentsPlusConfig();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable(PREFIX + "title"))
                .setAlwaysShowTabs(true)
                .setSavingRunnable(holder::save)
                .setAfterInitConsumer(EnchantmentsPlusConfigScreen::hideUnusedTabScrollButtons);
        ConfigEntryBuilder entries = builder.entryBuilder();

        // create category screens
        for (String categoryId : new String[]{"combat", "movement", "utility"}) {
            ConfigCategory category = builder.getOrCreateCategory(Component.translatable(PREFIX + "category." + categoryId));
            category.addEntry(entries.startTextDescription(Component.translatable(PREFIX + "settings_notice")
                    .append("\n").append(Component.translatable(PREFIX + "restart_notice"))).build());

            for (EnchantmentOption option : EnchantmentOption.values()) {
                if (option.category().equals(categoryId)) {
                    addEnchantment(category, entries, config, defaults, option);
                }
            }
        }
        return builder.build();
    }

    private static void hideUnusedTabScrollButtons(Screen screen) {
        if (!(screen instanceof ClothConfigScreen configScreen)
                || configScreen.getTabsMaximumScrolled() + 40 >= configScreen.width) {
            return;
        }

        // Cloth Config shows disabled scroll arrows, hide them
        for (var child : configScreen.children()) {
            if (child instanceof AbstractWidget widget && widget.getY() == 44 && widget.getWidth() == 12) {
                widget.visible = false;
            }
        }
    }

    private static void addEnchantment(ConfigCategory category, ConfigEntryBuilder entries,
                                       EnchantmentsPlusConfig config, EnchantmentsPlusConfig defaults,
                                       EnchantmentOption option) {
        String enchantmentKey = "enchantment.enchantmentsplus." + option.id();
        var section = entries.startSubCategory(Component.translatable(enchantmentKey)).setExpanded(false);
        section.add(entries.startTextDescription(Component.translatable(enchantmentKey + ".desc")).build());

        var enabled = entries.startBooleanToggle(Component.translatable(PREFIX + "option.enabled"), option.isEnabled(config))
                .setDefaultValue(option.isEnabled(defaults))
                .setTooltip(Component.translatable(PREFIX + "enabled_tooltip"))
                .setSaveConsumer(value -> option.setEnabled(config, value))
                .build();
        section.add(enabled);
        section.add(entries.startIntField(Component.translatable(PREFIX + "option.maximumLevel"), option.getMaxLevel(config))
                .setDefaultValue(option.getMaxLevel(defaults))
                .setMin(EnchantmentOption.MIN_LEVEL)
                .setMax(EnchantmentOption.MAX_LEVEL)
                .setTooltip(Component.translatable(PREFIX + "maximum_level_tooltip", option.getMaxLevel(defaults)))
                .setRequirement(Requirement.isTrue(enabled))
                .setSaveConsumer(value -> option.setMaxLevel(config, value))
                .build());

        String scalingNotice = option.scalesWithLevel() ? "scaling_notice" : "non_scaling_notice";
        section.add(entries.startTextDescription(Component.translatable(PREFIX + scalingNotice)).build());
        // Cloth Config's builder cannot customize focus handling; its only entry constructor is deprecated.
        @SuppressWarnings("deprecation")
        var focusSafeSection = new SubCategoryListEntry(Component.translatable(enchantmentKey), new ArrayList<>(section), false) {
            @Override
            public void setFocused(GuiEventListener listener) {
                // Cloth Config unfocuses even an unchanged child, which clears a nested text field's focus on repeated clicks. Match vanilla's identity guard.
                if (getFocused() != listener) {
                    super.setFocused(listener);
                }
            }
        };
        category.addEntry(focusSafeSection);
    }
}
