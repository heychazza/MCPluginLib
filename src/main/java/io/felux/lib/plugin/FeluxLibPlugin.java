package io.felux.lib.plugin;

import io.felux.lib.api.command.CommandManager;
import io.felux.lib.api.exception.InvalidMaterialException;
import io.felux.lib.api.item.ItemBuilder;
import io.felux.lib.api.item.ItemUtil;
import io.felux.lib.plugin.command.ASubCommand;
import io.felux.lib.plugin.command.YourMainCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class FeluxLibPlugin extends JavaPlugin implements Listener {

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

        try {
            new ItemBuilder("DIRT").withNBTString("some-key", "value");
        } catch (InvalidMaterialException e) {
            e.printStackTrace();
        }


    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        ItemBuilder itemBuilder = new ItemBuilder(Material.DIAMOND_SWORD)
                .withName("&cLucky Sword")
                .withLore("&7A powerful, strong sword.")
                .withEnchant(Enchantment.KNOCKBACK, 1)
                .withFlag(ItemFlag.HIDE_ENCHANTS)
                .withData(0)
                .withNBTString("sword-type", "strong");
        player.getInventory().addItem(itemBuilder.getItem());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        String swordType = ItemUtil.getNBTString(item, "sword-type");

        if (swordType != null)
            e.getPlayer().sendMessage("You have a " + swordType + " sword.");
    }
}
