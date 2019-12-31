package com.codeitforyou.lib.plugin.command;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.StringUtil;
import com.codeitforyou.lib.plugin.CIFYLibPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ASubCommand {
    @Command(permission = "feluxlib.sub", usage = "say <message>", aliases = {"say"}, requiredArgs = 1)
    public static void execute(final CommandSender sender, final CIFYLibPlugin plugin, final String[] args) {
        String message = args[0];
        Bukkit.getServer().broadcastMessage(StringUtil.translate("&c[Broadcast] &7" + message));
    }
}
