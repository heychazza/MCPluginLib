package com.octanna.lib.command.defaults;

import com.octanna.lib.command.Command;
import com.octanna.lib.command.CommandManager;
import com.octanna.lib.command.CommandValidator;
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
