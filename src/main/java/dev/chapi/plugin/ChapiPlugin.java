package dev.chapi.plugin;

import dev.chapi.api.exception.InvalidEnchantException;
import dev.chapi.api.exception.InvalidFlagException;
import dev.chapi.api.exception.InvalidLocationException;
import dev.chapi.api.exception.InvalidMaterialException;
import dev.chapi.api.item.ItemBuilder;
import dev.chapi.api.serializer.LocationSerializer;
import org.bukkit.plugin.java.JavaPlugin;

public class ChapiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            new ItemBuilder("DIRTS")
                    .withName("&6Golden Dirt")
                    .withLore("&7This dirt is well..", "&7MAGICAL!")
                    .withItemFlag("HIDE_ENCHANTSS")
                    .withEnchant("KNOCKBACKS", 1)
                    .getItem();

        } catch (InvalidEnchantException | InvalidFlagException | InvalidMaterialException e) {
            e.printStackTrace();
        }

        try {
            LocationSerializer.fromString("1;2;3;4");
        } catch (InvalidLocationException e) {
            e.printStackTrace();
        }
    }
}
