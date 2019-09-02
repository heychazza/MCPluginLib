package dev.chapi.general;

import org.bukkit.ChatColor;

public class StringUtil {
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
