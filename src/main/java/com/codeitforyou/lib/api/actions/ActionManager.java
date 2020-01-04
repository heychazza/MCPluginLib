package com.codeitforyou.lib.api.actions;

import com.codeitforyou.lib.api.general.StringUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ActionManager {
    private Map<String, Action> actions = Maps.newConcurrentMap();

    public void addAction(final String id, final Action action) {
        actions.put(id.toUpperCase(), action);
    }

    public Map<String, Action> getActions() {
        return actions;
    }

    public void addDefaults() {
        addAction("console", (player, data) -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), data));
        addAction("player", Player::performCommand);
        addAction("broadcast", (player, data) -> Bukkit.broadcastMessage(data));
        addAction("message", CommandSender::sendMessage);
        addAction("chat", Player::chat);
        addAction("close", (player, data) -> player.closeInventory());
    }

    public void runActions(final Player player, final List<String> items) {
        items.forEach(item -> {
            String actionData = !item.contains(" ") ? "" : StringUtil.translate(item.split(" ", 2)[1]).replace("%player%", player.getName());

            Action action = getAction(item);
            if (action != null) action.run(player, actionData);
            else Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actionData);
        });
    }

    public void runActions(final Player player, final String... items) {
        runActions(player, Arrays.asList(items));
    }

    public Action getAction(String item) {
        boolean singleAction = !item.contains(" ");

        String actionPrefix = singleAction ? item : item.split(" ", 2)[0].toUpperCase();
        String rawAction = StringUtils.substringBetween(actionPrefix, "[", "]");

        if (actions.containsKey(rawAction)) return actions.get(rawAction);
        return null;
    }
}
