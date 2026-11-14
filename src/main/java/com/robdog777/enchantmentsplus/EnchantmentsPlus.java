package com.robdog777.enchantmentsplus;

import com.robdog777.enchantmentsplus.config.ClothConfigIntegration;
import com.robdog777.enchantmentsplus.enchants.ConfigurableEnchantment;
import com.robdog777.enchantmentsplus.network.EnchantmentConfigNetworking;
import com.robdog777.enchantmentsplus.statuseffects.MoonRestEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantmentsPlus implements ModInitializer {
    public static final String MOD_ID = "enchantmentsplus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final boolean CLOTH_CONFIG_LOADED = FabricLoader.getInstance().isModLoaded("cloth-config");

    // moon png file from https://www.pngitem.com; copyright-free audio from https://freesound.org/
    public static final Identifier SWOOP = new Identifier(MOD_ID, "swoop");
    public static final Identifier BLURP = new Identifier(MOD_ID, "blurp");
    public static final Identifier WHOOSH = new Identifier(MOD_ID, "whoosh");
    public static final Identifier DENY = new Identifier(MOD_ID, "deny");
    public static final SoundEvent SwoopEvent = SoundEvent.of(SWOOP);
    public static final SoundEvent BlurpEvent = SoundEvent.of(BLURP);
    public static final SoundEvent WhooshEvent = SoundEvent.of(WHOOSH);
    public static final SoundEvent DenyEvent = SoundEvent.of(DENY);
    public static final StatusEffect MOONREST = new MoonRestEffect();

    public static final Enchantment BLAZEWALKER = new ConfigurableEnchantment("blazewalker", 2, 2, 4, 10, 10, 25, 10, "feet", "blaze");
    public static final Enchantment CUBICAL = new ConfigurableEnchantment("cubical", 5, 5, 2, 5, 8, 25, 8, "weapon", "damage");
    public static final Enchantment DUALLEAP = new ConfigurableEnchantment("dualleap", 1, 1, 8, 10, 10, 25, 10, "feet", "movement");
    public static final Enchantment ENDSLAYER = new ConfigurableEnchantment("endslayer", 5, 5, 2, 5, 8, 25, 8, "weapon", "damage");
    public static final Enchantment EXCAVATOR = new ConfigurableEnchantment("excavator", 3, 1, 8, 10, 10, 25, 10, "tool", "");
    public static final Enchantment FLASHFORGE = new ConfigurableEnchantment("flashforge", 1, 1, 8, 10, 10, 25, 10, "tool", "smelting");
    public static final Enchantment FROSTBITE = new ConfigurableEnchantment("frostbite", 2, 2, 4, 10, 20, 60, 20, "weapon", "elemental");
    public static final Enchantment HIKER = new ConfigurableEnchantment("hiker", 1, 1, 8, 5, 0, 41, 0, "feet", "");
    public static final Enchantment LEVITATION = new ConfigurableEnchantment("levitation", 2, 2, 4, 10, 20, 60, 20, "weapon", "elemental");
    public static final Enchantment LIFESTEAL = new ConfigurableEnchantment("lifesteal", 3, 2, 4, 10, 20, 60, 20, "weapon", "health");
    public static final Enchantment LUNARSIGHT = new ConfigurableEnchantment("lunarsight", 1, 1, 4, 1, 0, 41, 0, "head", "");
    public static final Enchantment MOONWALKER = new ConfigurableEnchantment("moonwalker", 2, 1, 8, 10, 10, 45, 10, "feet", "movement");
    public static final Enchantment MYSTICMIND = new ConfigurableEnchantment("mysticmind", 1, 1, 4, 5, 0, 41, 0, "head", "");
    public static final Enchantment PAYBACK = new ConfigurableEnchantment("payback", 3, 3, 5, 5, 8, 45, 8, "weapon", "health");
    public static final Enchantment RAIDER = new ConfigurableEnchantment("raider", 5, 5, 2, 5, 8, 25, 8, "weapon", "damage");
    public static final Enchantment SNIPER = new ConfigurableEnchantment("sniper", 3, 3, 4, 12, 20, 37, 20, "bow", "bow");
    public static final Enchantment STORMSTRIKE = new ConfigurableEnchantment("stormstrike", 3, 8, 8, 12, 20, 65, 20, "bow", "bow");
    public static final Enchantment THUNDERLORD = new ConfigurableEnchantment("thunderlord", 3, 8, 8, 12, 20, 65, 20, "weapon", "");
    public static final Enchantment TOXICSTRIKE = new ConfigurableEnchantment("toxicstrike", 2, 2, 4, 10, 20, 60, 20, "weapon", "elemental");

    public static final Enchantment[] ENCHANTMENTS = {BLAZEWALKER, CUBICAL, DUALLEAP, ENDSLAYER, EXCAVATOR,
            FLASHFORGE, FROSTBITE, HIKER, LEVITATION, LIFESTEAL, LUNARSIGHT, MOONWALKER, MYSTICMIND,
            PAYBACK, RAIDER, SNIPER, STORMSTRIKE, THUNDERLORD, TOXICSTRIKE};

    @Override
    public void onInitialize() {
        if (CLOTH_CONFIG_LOADED) {
            ClothConfigIntegration.load();
        }
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "moonresteffect"), MOONREST);
        Registry.register(Registries.SOUND_EVENT, SWOOP, SwoopEvent);
        Registry.register(Registries.SOUND_EVENT, BLURP, BlurpEvent);
        Registry.register(Registries.SOUND_EVENT, WHOOSH, WhooshEvent);
        Registry.register(Registries.SOUND_EVENT, DENY, DenyEvent);
        for (Enchantment enchantment : ENCHANTMENTS) {
            Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, ((ConfigurableEnchantment) enchantment).id()), enchantment);
        }
        EnchantmentConfigNetworking.register();
        LOGGER.info("enchantmentsplus is now loaded");
    }
}
