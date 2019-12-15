package io.felux.lib.plugin;

import io.felux.lib.api.command.Command;
import io.felux.lib.api.exception.InvalidInventoryException;
import io.felux.lib.api.exception.InvalidMaterialException;
import io.felux.lib.api.general.StringUtil;
import io.felux.lib.api.inventory.Inventory;
import io.felux.lib.api.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

public class GUITestCommand {
    @Command(permission = "lol", requiredArgs = 0)
    public static void execute(final CommandSender sender, final FeluxPlugin plugin, final String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            createInv(player);
        }
    }

    private static void createInv(Player p) {
        Inventory inventory = null;
        try {
            inventory = new Inventory(InventoryType.HOPPER, "Chapi | Inventory Test", JavaPlugin.getPlugin(FeluxPlugin.class));
            inventory.setItem(0, new ItemBuilder(Material.EMERALD.name()).withName("&a&lHmm..").withLore("&7I can only be shift clicked..", "&7Odd..").getItem(), (player, action) -> {
                if (action == ClickType.SHIFT_LEFT) {
                    player.closeInventory();
                    player.setLevel(100);
                    player.sendMessage(StringUtil.translate("&7You did it! &a(Insert 'wow' meme)"));
                } else {
                    player.sendMessage(StringUtil.translate("&cYou tried to " + action.name() + " this item.. but failed."));
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
