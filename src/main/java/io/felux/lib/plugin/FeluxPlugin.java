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

        CommandManager commandManager = new CommandManager(Arrays.asList(GUITesterCommand.class), "guitest", this);
        commandManager.setMainCommand(GUITestCommand.class);
        commandManager.getLocale().setNoPermission("&cYou cannot do that..");
        commandManager.getLocale().setUnknownCommand("&7Unknown, try /guitest help.");
        commandManager.getLocale().setUsage("&7Please use &b{usage}&7.");
        commandManager.getLocale().setPlayerOnly("&cConsole isn't currently supported.");
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
            inventory = new Inventory(InventoryType.HOPPER, "Chapi | Inventory Test", JavaPlugin.getPlugin(FeluxPlugin.class));
            inventory.setItem(0, new ItemBuilder(Material.EMERALD.name()).withName("&a&lHmm..").withLore("&7I can only be shift clicked..", "&7Odd..").getItem(), (player, action) -> {
                if(action == ClickType.SHIFT_LEFT) {
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

        assert inventory != null;
        inventory.open(p);
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        if (e.getMessage().equalsIgnoreCase("opengui")) createInv(e.getPlayer());
    }
}
