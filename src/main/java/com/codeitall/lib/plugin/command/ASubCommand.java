package com.codeitall.lib.plugin.command;

import com.codeitall.lib.api.command.Command;
import com.codeitall.lib.api.general.StringUtil;
import com.codeitall.lib.plugin.CIALibPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ASubCommand {
    @Command(permission = "feluxlib.sub", usage = "say <message>", aliases = {"say"}, requiredArgs = 1)
    public static void execute(final CommandSender sender, final CIALibPlugin plugin, final String[] args) {
        String message = args[0];
        Bukkit.getServer().broadcastMessage(StringUtil.translate("&c[Broadcast] &7" + message));
    }
}
