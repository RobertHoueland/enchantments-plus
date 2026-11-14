package com.robdog777.enchantmentsplus.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.ClothConfigScreen;
import me.shedaniel.clothconfig2.gui.entries.SubCategoryListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public final class EnchantmentsPlusConfigScreen {
    private static final String PREFIX = "text.autoconfig.enchantmentsplus.";

    private EnchantmentsPlusConfigScreen() { }

    public static Screen create(Screen parent) {
        var holder = ClothConfigIntegration.getConfigHolder();
        EnchantmentsPlusConfig config = holder.getConfig();
        EnchantmentsPlusConfig defaults = new EnchantmentsPlusConfig();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable(PREFIX + "title"))
                .setAlwaysShowTabs(true)
                .setSavingRunnable(holder::save)
                .setAfterInitConsumer(EnchantmentsPlusConfigScreen::hideUnusedTabScrollButtons);
        ConfigEntryBuilder entries = builder.entryBuilder();
        for (String categoryId : new String[]{"combat", "movement", "utility"}) {
            ConfigCategory category = builder.getOrCreateCategory(Text.translatable(PREFIX + "category." + categoryId));
            category.addEntry(entries.startTextDescription(Text.translatable(PREFIX + "settings_notice")
                    .append("\n").append(Text.translatable(PREFIX + "restart_notice"))).build());
            for (EnchantmentOption option : EnchantmentOption.values()) {
                if (option.category().equals(categoryId)) addEnchantment(category, entries, config, defaults, option);
            }
        }
        return builder.build();
    }

    private static void hideUnusedTabScrollButtons(Screen screen) {
        if (!(screen instanceof ClothConfigScreen configScreen)
                || configScreen.getTabsMaximumScrolled() + 40 >= configScreen.width) return;
        for (var child : configScreen.children()) {
            if (child instanceof ClickableWidget widget && widget.getY() == 44 && widget.getWidth() == 12) widget.visible = false;
        }
    }

    private static void clearChildFocus(Element element) {
        if (element instanceof ParentElement parent && parent.getFocused() != null) {
            clearChildFocus(parent.getFocused());
            parent.setFocused(null);
        }
        element.setFocused(false);
    }

    private static void addEnchantment(ConfigCategory category, ConfigEntryBuilder entries,
                                       EnchantmentsPlusConfig config, EnchantmentsPlusConfig defaults,
                                       EnchantmentOption option) {
        String key = "enchantment.enchantmentsplus." + option.id();
        var section = entries.startSubCategory(Text.translatable(key)).setExpanded(false);
        section.add(entries.startTextDescription(Text.translatable(key + ".desc")).build());
        var enabled = entries.startBooleanToggle(Text.translatable(PREFIX + "option.enabled"), option.isEnabled(config))
                .setDefaultValue(option.isEnabled(defaults))
                .setTooltip(Text.translatable(PREFIX + "enabled_tooltip"))
                .setSaveConsumer(value -> option.setEnabled(config, value)).build();
        section.add(enabled);
        var maximumLevel = entries.startIntField(Text.translatable(PREFIX + "option.maximumLevel"), option.getMaxLevel(config))
                .setDefaultValue(option.getMaxLevel(defaults))
                .setMin(EnchantmentOption.MIN_LEVEL).setMax(EnchantmentOption.MAX_LEVEL)
                .setTooltip(Text.translatable(PREFIX + "maximum_level_tooltip", option.getMaxLevel(defaults)))
                .setSaveConsumer(value -> option.setMaxLevel(config, value)).build();
        maximumLevel.setEditable(enabled.getValue());
        section.add(maximumLevel);
        String notice = option.scalesWithLevel() ? "scaling_notice" : "non_scaling_notice";
        section.add(entries.startTextDescription(Text.translatable(PREFIX + notice)).build());
        @SuppressWarnings("deprecation")
        var focusSafeSection = new SubCategoryListEntry(Text.translatable(key), new ArrayList<>(section), false) {
            @Override
            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight,
                               int mouseX, int mouseY, boolean hovered, float delta) {
                // Cloth Config 11 predates Requirement; update the entry's editability before rendering.
                maximumLevel.setEditable(enabled.getValue());
                super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, delta);
            }

            @Override
            public void setFocused(Element listener) {
                // Repeated clicks must not clear the focus of a nested maximum-level text field.
                if (getFocused() != listener) {
                    // Cloth 11 does not propagate losing focus to nested text fields.
                    if (getFocused() != null) clearChildFocus(getFocused());
                    super.setFocused(listener);
                }
            }
        };
        category.addEntry(focusSafeSection);
    }
}
