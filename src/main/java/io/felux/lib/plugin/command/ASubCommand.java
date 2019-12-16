package io.felux.lib.plugin.command;

import io.felux.lib.api.command.Command;
import io.felux.lib.api.general.StringUtil;
import io.felux.lib.plugin.FeluxLibPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ASubCommand {
    @Command(permission = "feluxlib.sub", usage = "say <message>", aliases = {"say"}, requiredArgs = 1)
    public static void execute(final CommandSender sender, final FeluxLibPlugin plugin, final String[] args) {
        String message = args[0];
        Bukkit.getServer().broadcastMessage(StringUtil.translate("&c[Broadcast] &7" + message));
    }
}
