package dev.chapi.plugin;

import dev.chapi.api.command.Command;
import org.bukkit.command.CommandSender;

public class GUITesterCommand {
    @Command(aliases = {"haha"}, usage = "guitest", permission = "lol", requiredArgs = 0)
    public static void execute(final CommandSender sender, final ChapiPlugin plugin, final String[] args) {
        sender.sendMessage("Lol, me too.");
        sender.sendMessage(plugin.getName());
    }
}
