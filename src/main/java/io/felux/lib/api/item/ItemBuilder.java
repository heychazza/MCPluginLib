package io.felux.lib.api.item;

import com.google.common.collect.Maps;
import de.tr7zw.changeme.nbtapi.NBTItem;
import io.felux.lib.api.exception.InvalidEnchantException;
import io.felux.lib.api.exception.InvalidFlagException;
import io.felux.lib.api.exception.InvalidMaterialException;
import io.felux.lib.api.general.StringUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder {
    private Material material;
    private int data;

    // Item Meta
    private String displayName;
    private List<String> lore;
    private List<ItemFlag> flags;
    private Map<Enchantment, Integer> enchants;

    // NBT Data
    private Map<String, String> nbtStrings;

    public ItemBuilder(String material) throws InvalidMaterialException {
        this.material = ItemUtil.getMaterial(material);
        data = 0;
        lore = new ArrayList<>();
        flags = new ArrayList<>();
        enchants = Maps.newConcurrentMap();

        nbtStrings = Maps.newConcurrentMap();
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

        NBTItem nbtItem = new NBTItem(item);
        nbtStrings.forEach(nbtItem::setString);
        return nbtItem.getItem();
    }
}

