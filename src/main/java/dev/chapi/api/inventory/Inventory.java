package dev.chapi.api.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Inventory implements Listener {

    public static Map<UUID, Inventory> inventoriesByUUID = new HashMap<>();
    public static Map<UUID, UUID> openInventories = new HashMap<>();

    private UUID uuid;
    private org.bukkit.inventory.Inventory yourInventory;
    private Map<Integer, GUIAction> actions;

    public Inventory(int invSize, String invName, JavaPlugin plugin) {
        uuid = UUID.randomUUID();
        yourInventory = Bukkit.createInventory(null, invSize, invName);
        actions = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
        inventoriesByUUID.put(getUuid(), this);
    }

    public void setItem(int slot, ItemStack stack, GUIAction action) {
        yourInventory.setItem(slot, stack);
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void setItem(int slot, ItemStack stack) {
        setItem(slot, stack, null);
    }

    public static Map<UUID, Inventory> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    public static Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public Map<Integer, GUIAction> getActions() {
        return actions;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void open(Player p) {
        openInventories.put(p.getUniqueId(), getUuid());
        p.openInventory(get());
    }

    public org.bukkit.inventory.Inventory get() {
        return yourInventory;
    }

    public void delete() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID u = openInventories.get(p.getUniqueId());
            if (u.equals(getUuid())) {
                p.closeInventory();
            }
        }
        inventoriesByUUID.remove(getUuid());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        UUID inventoryUUID = Inventory.openInventories.get(playerUUID);
        if (inventoryUUID != null) {
            e.setCancelled(true);
            Inventory gui = Inventory.getInventoriesByUUID().get(inventoryUUID);
            Inventory.GUIAction action = gui.getActions().get(e.getSlot());

            if (action != null) {
                action.click(player, e.getClick());
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Inventory.openInventories.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Inventory.openInventories.remove(e.getPlayer().getUniqueId());
    }

    public interface GUIAction {
        void click(Player player, ClickType action);
    }
}
