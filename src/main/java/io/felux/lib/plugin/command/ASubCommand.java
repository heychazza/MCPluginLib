package io.felux.lib.plugin.command;

import io.felux.lib.api.command.Command;
import io.felux.lib.api.exception.InvalidInventoryException;
import io.felux.lib.api.exception.InvalidMaterialException;
import io.felux.lib.api.general.StringUtil;
import io.felux.lib.api.inventory.Inventory;
import io.felux.lib.api.item.ItemBuilder;
import io.felux.lib.plugin.FeluxPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

public class ASubCommand {
    @Command(permission = "feluxlib.sub", usage = "say", aliases = {"say"}, requiredArgs = 1)
    public static void execute(final CommandSender sender, final FeluxPlugin plugin, final String[] args) {
        String message = args[0];
        Bukkit.getServer().broadcastMessage(StringUtil.translate("&c[Broadcast] &7" + message));
    }
}
