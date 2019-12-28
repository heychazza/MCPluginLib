package com.codeitall.lib.plugin.command;

import com.codeitall.lib.api.command.Command;
import com.codeitall.lib.api.exception.InvalidInventoryException;
import com.codeitall.lib.api.exception.InvalidMaterialException;
import com.codeitall.lib.api.general.StringUtil;
import com.codeitall.lib.api.inventory.Inventory;
import com.codeitall.lib.api.item.ItemBuilder;
import com.codeitall.lib.plugin.CIALibPlugin;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

public class YourMainCommand {
    @Command(permission = "feluxlib.gui", requiredArgs = 0)
    public static void execute(final CommandSender sender, final CIALibPlugin plugin, final String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            createInv(player);
        }
    }

    private static void createInv(Player p) {
        Inventory inventory = null;
        try {
            inventory = new Inventory(InventoryType.WORKBENCH, "Felux | Main Panel", JavaPlugin.getPlugin(CIALibPlugin.class));
            inventory.setItem(0, new ItemBuilder(Material.EMERALD.name()).withName("&a&lSome Button").withLore("&7I can only be shift clicked..", "&7Try me!").getItem(), (player, action) -> {
                if (action == ClickType.SHIFT_LEFT) {
                    player.closeInventory();
                    player.sendMessage(StringUtil.translate("&aWell done! &7You did it :)"));
                } else {
                    player.sendMessage(StringUtil.translate("&cHey! &7Nice try attempting to &c" + action.name() + " &7this item.."));
                }
            });
            inventory.addItem(new ItemBuilder(Material.DIAMOND.name()).getItem(), (player, action) -> player.sendMessage("Hi, you interacted with me using " + action.name() + "."));
        } catch (InvalidInventoryException | InvalidMaterialException ex) {
            ex.printStackTrace();
        }

        if (inventory != null) {
            inventory.open(p);
        }
    }
}
