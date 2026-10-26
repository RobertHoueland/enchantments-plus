package com.robdog777.enchantmentsplus.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "enchantmentsplus")
public class EnchantmentsPlusConfig implements ConfigData {
    public boolean enableBlazeWalker = true;
    public boolean enableCubical = true;
    public boolean enableDualLeap = true;
    public boolean enableEndSlayer = true;
    public boolean enableExcavator = true;
    public boolean enableFlashForge = true;
    public boolean enableFrostbite = true;
    public boolean enableHiker = true;
    public boolean enableLevitation = true;
    public boolean enableLifeSteal = true;
    public boolean enableLunarSight = true;
    public boolean enableMoonWalker = true;
    public boolean enableMysticMind = true;
    public boolean enablePayback = true;
    public boolean enableRaider = true;
    public boolean enableSniper = true;
    public boolean enableStormStrike = true;
    public boolean enableThunderlord = true;
    public boolean enableToxicStrike = true;

    // default levels
    public int maxLevelBlazeWalker = 2;
    public int maxLevelCubical = 5;
    public int maxLevelDualLeap = 1;
    public int maxLevelEndSlayer = 5;
    public int maxLevelExcavator = 3;
    public int maxLevelFlashForge = 1;
    public int maxLevelFrostbite = 2;
    public int maxLevelHiker = 1;
    public int maxLevelLevitation = 2;
    public int maxLevelLifeSteal = 3;
    public int maxLevelLunarSight = 1;
    public int maxLevelMoonWalker = 2;
    public int maxLevelMysticMind = 1;
    public int maxLevelPayback = 3;
    public int maxLevelRaider = 5;
    public int maxLevelSniper = 3;
    public int maxLevelStormStrike = 3;
    public int maxLevelThunderlord = 3;
    public int maxLevelToxicStrike = 2;

    @Override
    public void validatePostLoad() {
        for (EnchantmentOption option : EnchantmentOption.values()) {
            option.setMaxLevel(this, option.getMaxLevel(this));
        }
    }
}