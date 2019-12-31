package com.codeitforyou.lib.api.actions;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface Action {
    void run(final Player player, final String data);
}
