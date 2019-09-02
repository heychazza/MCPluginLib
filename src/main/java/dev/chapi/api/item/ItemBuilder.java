package dev.chapi.api.item;

import dev.chapi.api.exception.InvalidMaterialException;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ItemBuilder {

    private Material material;

    public ItemBuilder(String material, int amount) {
        this.material = Material.getMaterial(material.toUpperCase());
        if (this.material == null) {
            new InvalidMaterialException(material.toUpperCase() + " doesn't exist.").printStackTrace();
        }
    }
}

