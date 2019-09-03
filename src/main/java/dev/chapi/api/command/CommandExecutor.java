package dev.chapi.api.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private CommandManager commandManager;

    public CommandExecutor(CommandManager commandManager, JavaPlugin plugin) {
        this.commandManager = commandManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (args.length == 0) {
            return commandManager.handle(sender, null, new String[]{});
        } else {
            return commandManager.handle(sender, args[0], Arrays.stream(args).skip(1).toArray(String[]::new));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command bukkitCommand, String alias, String[] args) {
        String command = args[0];
        String[] commandArgs = Arrays.stream(args).skip(1).toArray(String[]::new);

        if (command.equals(""))
            return new ArrayList<String>() {{
                for (Map.Entry<String, Method> command : commandManager.getCommands().entrySet())
                        add(command.getKey());
            }};
        if (commandArgs.length == 0)
            return new ArrayList<String>() {{
                for (Map.Entry<String, Method> commandPair : commandManager.getCommands().entrySet())
                    if (commandPair.getKey().toLowerCase().startsWith(command.toLowerCase()))
                            add(commandPair.getKey());
            }};
        return null;
    }
}