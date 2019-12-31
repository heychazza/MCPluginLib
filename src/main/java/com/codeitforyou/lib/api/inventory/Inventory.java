package com.codeitforyou.lib.api.inventory;

import com.codeitforyou.lib.api.exception.InvalidInventoryException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
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
    private boolean persist;

    public Inventory(Object sizeOrType, String invName, JavaPlugin plugin) throws InvalidInventoryException {
        uuid = UUID.randomUUID();

        if (sizeOrType instanceof Integer) {
            yourInventory = Bukkit.createInventory(null, (Integer) sizeOrType, invName);
        } else if (sizeOrType instanceof InventoryType) {
            try {
                yourInventory = Bukkit.createInventory(null, (InventoryType) sizeOrType, invName);
            } catch (NoSuchFieldError e) {
                throw new InvalidInventoryException("You need to specify a valid inventory type.");
            }
        } else throw new InvalidInventoryException("You need to specify a valid inventory size or type.");

        actions = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
        inventoriesByUUID.put(getUuid(), this);
        persist = false;
    }

    public void setPersist(boolean persist) {
        this.persist = persist;
    }

    public void addItem(ItemStack stack, GUIAction action) {
        int slot = yourInventory.firstEmpty();
        yourInventory.addItem(stack);
        if (action != null) {
            actions.put(slot, action);
        }
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

    public boolean isPersistent() {
        return persist;
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
            if (openInventories.containsKey(p.getUniqueId())) {
                UUID u = openInventories.get(p.getUniqueId());
                if (u == getUuid()) {
                    openInventories.remove(u);
                }
            }
        }
        inventoriesByUUID.remove(getUuid());
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();

        UUID inventoryUUID = openInventories.get(playerUUID);
        if (inventoryUUID != null) {
            e.setCancelled(true);
            Inventory gui = getInventoriesByUUID().get(inventoryUUID);
            GUIAction action = gui.getActions().get(e.getSlot());

            if (action != null) {
                action.click(player, e.getClick());
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (!isPersistent()) {
            delete();
        } else {
            openInventories.remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
    }

    public interface GUIAction {
        void click(Player player, ClickType action);
    }
}
