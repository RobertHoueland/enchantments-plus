package com.robdog777.enchantmentsplus.enchants;

import com.robdog777.enchantmentsplus.config.EnchantmentSettings;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

/** Java registry equivalent of the modern branch's data-driven enchantment definitions. */
public final class ConfigurableEnchantment extends Enchantment {
    private final String id;
    private final int defaultMaxLevel;
    private final int weight;
    private final int anvilCost;
    private final int minBase;
    private final int minPerLevel;
    private final int maxBase;
    private final int maxPerLevel;
    private final String exclusiveSet;

    public ConfigurableEnchantment(String id, int defaultMaxLevel, int weight, int anvilCost,
                                   int minBase, int minPerLevel, int maxBase, int maxPerLevel,
                                   String items, String exclusiveSet) {
        super(switch (anvilCost) {
            case 2 -> Rarity.UNCOMMON;
            case 4, 5 -> Rarity.RARE;
            default -> Rarity.VERY_RARE;
        }, switch (items) {
            case "feet" -> EnchantmentTarget.ARMOR_FEET;
            case "head" -> EnchantmentTarget.ARMOR_HEAD;
            case "tool" -> EnchantmentTarget.DIGGER;
            case "bow" -> EnchantmentTarget.BOW;
            default -> EnchantmentTarget.WEAPON;
        }, new EquipmentSlot[]{switch (items) {
            case "feet" -> EquipmentSlot.FEET;
            case "head" -> EquipmentSlot.HEAD;
            default -> EquipmentSlot.MAINHAND;
        }});
        this.id = id;
        this.defaultMaxLevel = defaultMaxLevel;
        this.weight = weight;
        this.anvilCost = anvilCost;
        this.minBase = minBase;
        this.minPerLevel = minPerLevel;
        this.maxBase = maxBase;
        this.maxPerLevel = maxPerLevel;
        this.exclusiveSet = exclusiveSet;
    }

    public String id() { return id; }
    public int weight() { return weight; }
    public int anvilCost() { return anvilCost; }
    public int defaultMaxLevel() { return defaultMaxLevel; }

    @Override
    public int getMaxLevel() { return EnchantmentSettings.maxLevel(this); }

    @Override
    public int getMinPower(int level) { return minBase + minPerLevel * (level - 1); }

    @Override
    public int getMaxPower(int level) { return maxBase + maxPerLevel * (level - 1); }

    @Override
    public boolean isTreasure() { return id.equals("blazewalker"); }

    @Override
    public boolean isAvailableForRandomSelection() { return EnchantmentSettings.enabled(this); }

    @Override
    public boolean isAvailableForEnchantedBookOffer() { return EnchantmentSettings.enabled(this); }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        // Swords are the table's primary items; axes also support weapon enchantments at an anvil.
        return super.isAcceptableItem(stack) || target == EnchantmentTarget.WEAPON && stack.getItem() instanceof AxeItem;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if (!super.canAccept(other)) return false;
        if (!exclusiveSet.isEmpty() && other instanceof ConfigurableEnchantment custom
                && exclusiveSet.equals(custom.exclusiveSet)) return false;
        return switch (exclusiveSet) {
            case "blaze" -> other != Enchantments.DEPTH_STRIDER && other != Enchantments.FROST_WALKER;
            case "damage" -> !(other instanceof DamageEnchantment);
            case "elemental" -> other != Enchantments.FIRE_ASPECT;
            case "smelting" -> other != Enchantments.SILK_TOUCH;
            default -> true;
        };
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        // Applied once by the attack mixins, using the actual attacking weapon.
        // Vanilla 1.20.1 invokes this callback for all equipped items and can repeat the main hand.
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        // Mystic Mind is applied once by LivingEntityMixin, using only the head slot.
    }
}
