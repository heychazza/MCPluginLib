package com.codeitforyou.lib.plugin;

import com.codeitforyou.lib.api.actions.ActionManager;
import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.lib.api.general.PAPIUtil;
import com.codeitforyou.lib.api.item.ItemBuilder;
import com.codeitforyou.lib.api.item.ItemUtil;
import com.codeitforyou.lib.plugin.command.ASubCommand;
import com.codeitforyou.lib.plugin.command.YourMainCommand;
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
import java.util.List;

public class CIFYLibPlugin extends JavaPlugin implements Listener {
    private ActionManager actionManager;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        CommandManager commandManager = new CommandManager(Arrays.asList(ASubCommand.class), "cifylib", this);
        commandManager.setMainCommand(YourMainCommand.class);
        CommandManager.getLocale().setNoPermission("&cYou cannot do that..");
        CommandManager.getLocale().setUnknownCommand("&7Unknown, try /codeitall help.");
        CommandManager.getLocale().setUsage("&7Please use &b{usage}&7.");
        CommandManager.getLocale().setPlayerOnly("&cConsole isn't currently supported.");

        // Example with referencing the configuration file.
        CommandManager.getLocale().setNoPermission(getConfig().getString("messages.permission", "&cNo permission to do that."));

        actionManager = new ActionManager();
        actionManager.addDefaults();
        actionManager.addAction("console", (player, data) -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), data));
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

        // Add our custom item to the players inventory.
        player.getInventory().addItem(itemBuilder.getItem());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        String swordType = ItemUtil.getNBTString(item, "sword-type");

        if (swordType != null) {
            List<String> someActions = Arrays.asList("[console] tell %player% You clicked a " + swordType + " sword!", "[chat] I love my new sword! :o");
            actionManager.runActions(player, someActions);
        }


    }
}
