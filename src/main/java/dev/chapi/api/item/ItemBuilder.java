package dev.chapi.api.item;

import dev.chapi.api.exception.InvalidEnchantException;
import dev.chapi.api.exception.InvalidFlagException;
import dev.chapi.api.exception.InvalidMaterialException;
import dev.chapi.api.general.StringUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;

    public ItemBuilder(String material) throws InvalidMaterialException {
        Material materialObj = Material.getMaterial(material.toUpperCase());
        if (materialObj == null) {
            throw new InvalidMaterialException(material.toUpperCase() + " doesn't exist.");
        }

        item = new ItemStack(item);
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
            flag = flag.toUpperCase();
            ItemFlag itemFlag = null;
            for (ItemFlag e : ItemFlag.values()) {
                if (e.name().equalsIgnoreCase(flag)) {
                    itemFlag = e;
                    break;
                }
            }

            if (itemFlag == null) {
                throw new InvalidFlagException(flag + " doesn't exist.");
            }
            itemMeta.addItemFlags(ItemFlag.valueOf(flag));
        }
        return this;
    }

    public ItemBuilder withEnchant(String enchant, int level) throws InvalidEnchantException {
        enchant = enchant.toUpperCase();

        Enchantment enchantment = null;
        for (Enchantment e : Enchantment.values()) {
            if (e.getName().equalsIgnoreCase(enchant)) {
                enchantment = e;
                break;
            }
        }

        if (enchantment == null) {
            throw new InvalidEnchantException(enchant + " doesn't exist.");
        }
        itemMeta.addEnchant(enchantment, level, false);
        return this;
    }

    public ItemStack getItem() {
        return item;
    }

}

