package com.robdog777.enchantmentsplus.config;

import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

// Keep this order aligned with EnchantmentsPlus.ENCHANTMENT_KEYS
public enum EnchantmentOption {
    BLAZE_WALKER("blazewalker", "movement", true,
            c -> c.enableBlazeWalker, (c, v) -> c.enableBlazeWalker = v,
            c -> c.maxLevelBlazeWalker, (c, v) -> c.maxLevelBlazeWalker = v),
    CUBICAL("cubical", "combat", true,
            c -> c.enableCubical, (c, v) -> c.enableCubical = v,
            c -> c.maxLevelCubical, (c, v) -> c.maxLevelCubical = v),
    DUAL_LEAP("dualleap", "movement", true,
            c -> c.enableDualLeap, (c, v) -> c.enableDualLeap = v,
            c -> c.maxLevelDualLeap, (c, v) -> c.maxLevelDualLeap = v),
    END_SLAYER("endslayer", "combat", true,
            c -> c.enableEndSlayer, (c, v) -> c.enableEndSlayer = v,
            c -> c.maxLevelEndSlayer, (c, v) -> c.maxLevelEndSlayer = v),
    EXCAVATOR("excavator", "utility", true,
            c -> c.enableExcavator, (c, v) -> c.enableExcavator = v,
            c -> c.maxLevelExcavator, (c, v) -> c.maxLevelExcavator = v),
    FLASH_FORGE("flashforge", "utility", false,
            c -> c.enableFlashForge, (c, v) -> c.enableFlashForge = v,
            c -> c.maxLevelFlashForge, (c, v) -> c.maxLevelFlashForge = v),
    FROSTBITE("frostbite", "combat", true,
            c -> c.enableFrostbite, (c, v) -> c.enableFrostbite = v,
            c -> c.maxLevelFrostbite, (c, v) -> c.maxLevelFrostbite = v),
    HIKER("hiker", "movement", true,
            c -> c.enableHiker, (c, v) -> c.enableHiker = v,
            c -> c.maxLevelHiker, (c, v) -> c.maxLevelHiker = v),
    LEVITATION("levitation", "combat", true,
            c -> c.enableLevitation, (c, v) -> c.enableLevitation = v,
            c -> c.maxLevelLevitation, (c, v) -> c.maxLevelLevitation = v),
    LIFE_STEAL("lifesteal", "combat", true,
            c -> c.enableLifeSteal, (c, v) -> c.enableLifeSteal = v,
            c -> c.maxLevelLifeSteal, (c, v) -> c.maxLevelLifeSteal = v),
    LUNAR_SIGHT("lunarsight", "utility", false,
            c -> c.enableLunarSight, (c, v) -> c.enableLunarSight = v,
            c -> c.maxLevelLunarSight, (c, v) -> c.maxLevelLunarSight = v),
    MOON_WALKER("moonwalker", "movement", true,
            c -> c.enableMoonWalker, (c, v) -> c.enableMoonWalker = v,
            c -> c.maxLevelMoonWalker, (c, v) -> c.maxLevelMoonWalker = v),
    MYSTIC_MIND("mysticmind", "utility", false,
            c -> c.enableMysticMind, (c, v) -> c.enableMysticMind = v,
            c -> c.maxLevelMysticMind, (c, v) -> c.maxLevelMysticMind = v),
    PAYBACK("payback", "combat", true,
            c -> c.enablePayback, (c, v) -> c.enablePayback = v,
            c -> c.maxLevelPayback, (c, v) -> c.maxLevelPayback = v),
    RAIDER("raider", "combat", true,
            c -> c.enableRaider, (c, v) -> c.enableRaider = v,
            c -> c.maxLevelRaider, (c, v) -> c.maxLevelRaider = v),
    SNIPER("sniper", "combat", true,
            c -> c.enableSniper, (c, v) -> c.enableSniper = v,
            c -> c.maxLevelSniper, (c, v) -> c.maxLevelSniper = v),
    STORM_STRIKE("stormstrike", "combat", true,
            c -> c.enableStormStrike, (c, v) -> c.enableStormStrike = v,
            c -> c.maxLevelStormStrike, (c, v) -> c.maxLevelStormStrike = v),
    THUNDERLORD("thunderlord", "combat", true,
            c -> c.enableThunderlord, (c, v) -> c.enableThunderlord = v,
            c -> c.maxLevelThunderlord, (c, v) -> c.maxLevelThunderlord = v),
    TOXIC_STRIKE("toxicstrike", "combat", true,
            c -> c.enableToxicStrike, (c, v) -> c.enableToxicStrike = v,
            c -> c.maxLevelToxicStrike, (c, v) -> c.maxLevelToxicStrike = v);

    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 99;
    private static final EnchantmentOption[] OPTIONS = values();

    private final String id;
    private final String category;
    private final boolean scalesWithLevel;
    private final Predicate<EnchantmentsPlusConfig> enabledGetter;
    private final BiConsumer<EnchantmentsPlusConfig, Boolean> enabledSetter;
    private final ToIntFunction<EnchantmentsPlusConfig> levelGetter;
    private final ObjIntConsumer<EnchantmentsPlusConfig> levelSetter;

    EnchantmentOption(String id, String category, boolean scalesWithLevel,
                      Predicate<EnchantmentsPlusConfig> enabledGetter,
                      BiConsumer<EnchantmentsPlusConfig, Boolean> enabledSetter,
                      ToIntFunction<EnchantmentsPlusConfig> levelGetter,
                      ObjIntConsumer<EnchantmentsPlusConfig> levelSetter) {
        this.id = id;
        this.category = category;
        this.scalesWithLevel = scalesWithLevel;
        this.enabledGetter = enabledGetter;
        this.enabledSetter = enabledSetter;
        this.levelGetter = levelGetter;
        this.levelSetter = levelSetter;
    }

    public static EnchantmentOption byIndex(int index) {
        return index >= 0 && index < OPTIONS.length ? OPTIONS[index] : null;
    }

    public static EnchantmentOption byId(String id) {
        for (EnchantmentOption option : OPTIONS) {
            if (option.id.equals(id)) {
                return option;
            }
        }
        return null;
    }

    public String id() {
        return id;
    }

    public String category() {
        return category;
    }

    public boolean scalesWithLevel() {
        return scalesWithLevel;
    }

    public boolean isEnabled(EnchantmentsPlusConfig config) {
        return enabledGetter.test(config);
    }

    public void setEnabled(EnchantmentsPlusConfig config, boolean enabled) {
        enabledSetter.accept(config, enabled);
    }

    public int getMaxLevel(EnchantmentsPlusConfig config) {
        return Math.clamp(levelGetter.applyAsInt(config), MIN_LEVEL, MAX_LEVEL);
    }

    public void setMaxLevel(EnchantmentsPlusConfig config, int level) {
        levelSetter.accept(config, Math.clamp(level, MIN_LEVEL, MAX_LEVEL));
    }
}
