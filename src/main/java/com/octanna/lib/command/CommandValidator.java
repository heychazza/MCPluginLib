package com.octanna.lib.command;

import org.bukkit.command.CommandSender;

public interface CommandValidator {
    boolean canExecute(CommandSender sender, Command command, CommandManager commandManager);
}
