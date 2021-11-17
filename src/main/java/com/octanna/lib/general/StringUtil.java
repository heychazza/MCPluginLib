package com.octanna.lib.general;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {
    /**
     * Translate a message string to a translated string.
     *
     * @param message
     * @return String
     */
    public static String translate(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(String... message) {
        return Arrays.stream(message).map(StringUtil::translate).collect(Collectors.toList());
    }

    public static List<String> translate(List<String> message) {
        return message.stream().map(StringUtil::translate).collect(Collectors.toList());
    }
}
