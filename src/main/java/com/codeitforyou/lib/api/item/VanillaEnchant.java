package com.codeitforyou.lib.api.item;

import java.util.Arrays;

public enum VanillaEnchant
{
    POWER("ARROW_DAMAGE"),
    FLAME("ARROW_FIRE"),
    INFINITY("ARROW_INFINITE"),
    PUNCH("ARROW_KNOCKBACK"),
    SHARPNESS("DAMAGE_ALL"),
    BANE_OF_ARTHROPODS("DAMAGE_ARTHROPODS"),
    SMITE("DAMAGE_UNDEAD"),
    DEPTH_STRIDER("DEPTH_STRIDER"),
    EFFICIENCY("DIG_SPEED"),
    UNBREAKING("DURABILITY"),
    FIRE_ASPECT("FIRE_ASPECT"),
    KNOCKBACK("KNOCKBACK"),
    FORTUNE("LOOT_BONUS_BLOCKS"),
    LOOTING("LOOT_BONUS_MOBS"),
    LUCK_OF_THE_SEA("LUCK"),
    LURE("LURE"),
    RESPIRATION("OXYGEN"),
    PROTECTION("PROTECTION_ENVIRONMENTAL"),
    BLAST_PROTECTION("PROTECTION_EXPLOSIONS"),
    FEATHER_FALLING("PROTECTION_FALL"),
    FIRE_PROTECTION("PROTECTION_FIRE"),
    PROJECTILE_PROTECTION("PROTECTION_PROJECTILE"),
    SILK_TOUCH("SILK_TOUCH"),
    THORNS("THORNS"),
    AQUA_AFFINITY("WATER_WORKER"),
    CURSE_OF_BINDING("BINDING_CURSE"),
    MENDING("MENDING"),
    FROST_WALKER("FROST_WALKER"),
    CURSE_OF_VANISHING("VANISHING_CURSE");

    private final String vanillaName;

    VanillaEnchant(final String vanillaName) {
        this.vanillaName = vanillaName;
    }
    
    public String getVanillaName() {
        return this.vanillaName;
    }
    
    @SuppressWarnings("deprecation")
    public org.bukkit.enchantments.Enchantment getEnchantment() {
        return org.bukkit.enchantments.Enchantment.getByName(this.vanillaName);
    }
    
    public static boolean exists(final String bukkitName) {
        return Arrays.stream(values()).anyMatch(enchant -> enchant.name().equals(bukkitName));
    }
}
