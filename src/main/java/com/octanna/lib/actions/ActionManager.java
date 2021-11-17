package com.octanna.lib.actions;

import com.cryptomorin.xseries.XSound;
import com.octanna.lib.general.PAPIUtil;
import com.octanna.lib.general.StringUtil;
import com.google.common.collect.Maps;
import net.md_5.bungee.chat.ComponentSerializer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ActionManager {
    private final Map<String, Action> actions;

    public void addAction(final String id, final Action action) {
        actions.put(id.toUpperCase(), action);
    }

    public Map<String, Action> getActions() {
        return actions;
    }

    private final JavaPlugin plugin;

    public ActionManager(JavaPlugin plugin) {
        this.plugin = plugin;
        actions = Maps.newConcurrentMap();
    }

    public void addDefaults() {
        addAction("console", (player, data) -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), data));
        addAction("player", Player::performCommand);
        addAction("broadcast", (player, data) -> Bukkit.broadcastMessage(data));
        addAction("message", CommandSender::sendMessage);
        addAction("chat", Player::chat);
        addAction("close", (player, data) -> player.closeInventory());
        addAction("sound", (player, data) -> XSound.matchXSound(data).ifPresent(sound -> sound.play(player)));
        addAction("json", (player, data) -> player.spigot().sendMessage(ComponentSerializer.parse(data)));

    }

    public void runActions(final Player player, final List<String> items) {
        items.forEach(item -> {
            String actionData = !item.contains(" ") ? "" : PAPIUtil.parse(player, StringUtil.translate(item.split(" ", 2)[1]).replace("%player%", player.getName()));
            Action action = getAction(item);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (action != null) action.run(player, actionData);
                    else Bukkit.dispatchCommand(Bukkit.getConsoleSender(), item);
                }
            }.runTask(plugin);
        });
    }

    public void runActions(final Player player, final String... items) {
        runActions(player, Arrays.asList(items));
    }

    public Action getAction(String item) {
        boolean singleAction = !item.contains(" ");

        String actionPrefix = singleAction ? item : item.split(" ", 2)[0].toUpperCase();
        String rawAction = StringUtils.substringBetween(actionPrefix, "[", "]");

        if (rawAction != null && actions.containsKey(rawAction)) return actions.get(rawAction);
        return null;
    }
}
