package com.codeitforyou.lib.plugin;

import com.codeitforyou.lib.api.actions.ActionManager;
import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.lib.api.general.StringUtil;
import com.codeitforyou.lib.api.item.ItemBuilder;
import com.codeitforyou.lib.api.item.ItemUtil;
import com.codeitforyou.lib.plugin.command.ASubCommand;
import com.codeitforyou.lib.plugin.command.YourMainCommand;
import com.google.common.collect.Maps;
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
import java.util.Map;
import java.util.UUID;

public class CIFYLibPlugin extends JavaPlugin implements Listener {
    private ActionManager actionManager;

    // Test trigger for Discord notifying #3.
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        CommandManager commandManager = new CommandManager(Arrays.asList(ASubCommand.class), "cifylib", this);
        commandManager.setMainCommand(YourMainCommand.class);
        commandManager.getLocale().setNoPermission("&cYou cannot do that..");
        commandManager.getLocale().setUnknownCommand("&7Unknown, try /codeitall help.");
        commandManager.getLocale().setUsage("&7Please use &b{usage}&7.");
        commandManager.getLocale().setPlayerOnly("&cConsole isn't currently supported.");

        // Example with referencing the configuration file.
        commandManager.getLocale().setNoPermission(getConfig().getString("messages.permission", "&cNo permission to do that."));

        actionManager = new ActionManager(this);
        actionManager.addDefaults();
        actionManager.addAction("color", (player, data) -> colors.put(player.getUniqueId(), data));
    }

    private Map<UUID, String> colors = Maps.newConcurrentMap();

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
            List<String> someActions = Arrays.asList("[console] tell %player% You clicked a " + swordType + " sword!", "[chat] I love my new sword! :o", "say lets test!",
                    "[COLOR] RED");
            actionManager.runActions(player, someActions);
            player.sendMessage(StringUtil.translate("&7Your current color is: &f" + colors.getOrDefault(player.getUniqueId(), null)));
        }
    }
}
