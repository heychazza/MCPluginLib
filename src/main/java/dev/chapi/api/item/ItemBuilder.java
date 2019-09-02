package dev.chapi.api.item;

import dev.chapi.api.exception.InvalidMaterialException;
import dev.chapi.general.StringUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;

    public ItemBuilder(String material) {
        Material materialObj = Material.getMaterial(material.toUpperCase());
        if (materialObj == null) {
            new InvalidMaterialException(material.toUpperCase() + " doesn't exist.").printStackTrace();
        }

        item = new ItemStack(item);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder withName(String displayName) {
        itemMeta.setDisplayName(StringUtil.translate(displayName));
        return this;
    }

}

