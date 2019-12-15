package io.felux.lib.plugin;

import io.felux.lib.api.command.CommandManager;
import io.felux.lib.api.exception.InvalidInventoryException;
import io.felux.lib.api.exception.InvalidMaterialException;
import io.felux.lib.api.general.StringUtil;
import io.felux.lib.api.inventory.Inventory;
import io.felux.lib.api.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class FeluxPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        CommandManager commandManager = new CommandManager(Arrays.asList(), "opengui", this);
        commandManager.setMainCommand(GUITestCommand.class);
        commandManager.getLocale().setNoPermission("&cYou cannot do that..");
        commandManager.getLocale().setUnknownCommand("&7Unknown, try /guitest help.");
        commandManager.getLocale().setUsage("&7Please use &b{usage}&7.");
        commandManager.getLocale().setPlayerOnly("&cConsole isn't currently supported.");

        commandManager.getLocale().setNoPermission(getConfig().getString("messages.permission", "&cNo permission to do that."));
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
}
