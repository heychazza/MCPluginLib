package com.octanna.lib.general;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PAPIUtil {
    private static String parsePlaceholders(OfflinePlayer p, String text) {
        return PlaceholderAPI.setPlaceholders(p, text);
    }

    public static String parse(OfflinePlayer p, String text) {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null ? parsePlaceholders(p, text) : text;
    }
}
