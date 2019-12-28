package com.codeitall.lib.api.command;

import org.bukkit.command.CommandSender;

public interface CommandValidator {
    boolean canExecute(CommandSender sender, Command command);
}
