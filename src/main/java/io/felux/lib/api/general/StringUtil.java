package io.felux.lib.api.general;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(String... message) {
        return Arrays.stream(message).map(StringUtil::translate).collect(Collectors.toList());
    }

    public static List<String> translate(List<String> message) {
        return message.stream().map(StringUtil::translate).collect(Collectors.toList());
    }
}
