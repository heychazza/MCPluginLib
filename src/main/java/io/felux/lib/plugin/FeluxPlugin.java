package io.felux.lib.plugin;

import io.felux.lib.api.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class FeluxPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        CommandManager commandManager = new CommandManager(Arrays.asList(ASubCommand.class), "feluxlib", this);
        commandManager.setMainCommand(YourMainCommand.class);
        commandManager.getLocale().setNoPermission("&cYou cannot do that..");
        commandManager.getLocale().setUnknownCommand("&7Unknown, try /feluxlib help.");
        commandManager.getLocale().setUsage("&7Please use &b{usage}&7.");
        commandManager.getLocale().setPlayerOnly("&cConsole isn't currently supported.");

        // Example with referencing the configuration file.
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
