package dev.chapi.api.item;

import dev.chapi.api.exception.InvalidEnchantException;
import dev.chapi.api.exception.InvalidFlagException;
import dev.chapi.api.exception.InvalidMaterialException;
import dev.chapi.api.general.StringUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;

    public ItemBuilder(String material) throws InvalidMaterialException {
        item = new ItemStack(ItemUtil.getMaterial(material));
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder withName(String displayName) {
        itemMeta.setDisplayName(StringUtil.translate(displayName));
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        itemMeta.setLore(StringUtil.translate(lore));
        return this;
    }

    public ItemBuilder withItemFlag(ItemFlag... flags) {
        for (ItemFlag flag : flags) {
            itemMeta.addItemFlags(flag);
        }
        return this;
    }

    public ItemBuilder withItemFlag(String... flags) throws InvalidFlagException {
        for (String flag : flags) {
            itemMeta.addItemFlags(ItemUtil.getFlag(flag));
        }
        return this;
    }

    public ItemBuilder withEnchant(Enchantment enchant, int level) {
        itemMeta.addEnchant(enchant, level, false);
        return this;
    }

    public ItemBuilder withEnchant(String enchant, int level) throws InvalidEnchantException {
        itemMeta.addEnchant(ItemUtil.getEnchantment(enchant), level, false);
        return this;
    }

    public ItemStack getItem() {
        return item;
    }
}

