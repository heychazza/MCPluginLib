package io.felux.lib.api.general;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(String... message) {
        List<String> lore = new ArrayList<>();
        for (String msg : message) {
            lore.add(translate(msg));
        }
        return lore;
    }

    public static List<String> translate(List<String> message) {
        List<String> lore = new ArrayList<>();
        for (String msg : message) {
            lore.add(translate(msg));
        }
        return lore;
    }
}
