package dev.chapi.plugin;

import dev.chapi.api.exception.InvalidInventoryException;
import dev.chapi.api.exception.InvalidMaterialException;
import dev.chapi.api.inventory.Inventory;
import dev.chapi.api.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChapiPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
/*        try {
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
        }*/


    }

    private void createInv(Player p) {
        Inventory inventory = null;
        try {
            inventory = new Inventory(18, "Chapi | Inventory Test", JavaPlugin.getPlugin(ChapiPlugin.class));
            inventory.setItem(1, new ItemBuilder(Material.DIAMOND.name()).getItem(), (player, action) -> player.sendMessage("Hi, you interacted with me using " + action.name() + "."));
        } catch (InvalidInventoryException | InvalidMaterialException ex) {
            ex.printStackTrace();
        }

        inventory.open(p);
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        createInv(e.getPlayer());
    }
}
