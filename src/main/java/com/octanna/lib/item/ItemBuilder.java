package com.octanna.lib.item;

import com.octanna.lib.exception.InvalidEnchantException;
import com.octanna.lib.exception.InvalidFlagException;
import com.octanna.lib.exception.InvalidMaterialException;
import com.octanna.lib.general.StringUtil;
import com.octanna.lib.nbt.NBT;
import com.google.common.collect.Maps;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemBuilder {
    private Material material;
    private int data = 0;

    // Item Meta
    private String displayName = "";
    private List<String> lore = new ArrayList<>();
    private List<ItemFlag> flags = new ArrayList<>();
    private final Map<Enchantment, Integer> enchants = Maps.newConcurrentMap();

    // NBT Data
    private final Map<String, String> nbtStrings = Maps.newConcurrentMap();

    private String skullOwner;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(String material) throws InvalidMaterialException {
        this.material = ItemUtil.getMaterial(material);
    }

    public ItemBuilder withData(int data) {
        this.data = data;
        return this;
    }

    public ItemBuilder withName(String displayName) {
        this.displayName = StringUtil.translate(displayName);
        return this;
    }

    public ItemBuilder withLore(String lore) {
        this.lore = Collections.singletonList(StringUtil.translate(lore));
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        this.lore = StringUtil.translate(lore);
        return this;
    }

    public ItemBuilder withLore(List<String> lore) {
        this.lore = StringUtil.translate(lore);
        return this;
    }

    public ItemBuilder withFlag(ItemFlag... flags) {
        this.flags = Arrays.asList(flags);
        return this;
    }

    public ItemBuilder withFlag(String... flags) throws InvalidFlagException {
        for (String flag : flags) {
            this.flags.add(ItemUtil.getFlag(flag));
        }
        return this;
    }

    public ItemBuilder withEnchant(Enchantment enchant, int level) {
        this.enchants.put(enchant, level);
        return this;
    }

    public ItemBuilder withEnchant(String enchant, int level) throws InvalidEnchantException {
        this.enchants.put(ItemUtil.getEnchantment(enchant), level);
        return this;
    }

    public ItemBuilder withNBTString(String key, String value) {
        this.nbtStrings.put(key, value);
        return this;
    }

    public ItemBuilder withSkullOwner(String owner) {
        skullOwner = owner;
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemStack getItem() {
        ItemStack item = new ItemStack(material);
        item.setDurability((short) data);

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(displayName);
            itemMeta.setLore(lore);

            flags.forEach(itemMeta::addItemFlags);

            enchants.forEach((enchantment, value) -> {
                int level = value;
                item.addUnsafeEnchantment(enchantment, level);
            });
        }

        item.setItemMeta(itemMeta);

        if(skullOwner != null) {
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            if (skullMeta != null) {
                skullMeta.setOwner(skullOwner);
            }

            item.setItemMeta(skullMeta);
        }

        NBT nbtItem = NBT.get(item);
        nbtStrings.forEach(nbtItem::setString);
        return nbtItem.apply(item);
    }
}

