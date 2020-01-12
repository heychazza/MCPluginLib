package com.codeitforyou.lib.api.command.defaults;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.command.CommandManager;
import com.codeitforyou.lib.api.command.CommandValidator;
import org.bukkit.command.CommandSender;

public class DefaultCommandValidator implements CommandValidator {
    public boolean canExecute(CommandSender sender, Command command, CommandManager commandManager) {
        if (!command.permission().isEmpty() && !sender.hasPermission(command.permission())) {
            sender.sendMessage(commandManager.getLocale().getNoPermission());
            return false;
        }
        return true;
    }
}
