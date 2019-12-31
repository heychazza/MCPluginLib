package com.codeitforyou.lib.api.command;

import com.codeitforyou.lib.api.command.defaults.DefaultCommandValidator;
import com.codeitforyou.lib.api.general.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {

    private JavaPlugin plugin;
    private Map<String, Method> commands = new HashMap<>();

    private String mainCommand;
    private Method mainCommandMethod;
    private static Locale locale;
    private CommandValidator commandValidator;

    public static Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        CommandManager.locale = locale;
    }

    public Map<String, Method> getCommands() {
        return commands;
    }

    public void setCommandValidator(CommandValidator commandValidator) {
        this.commandValidator = commandValidator;
    }

    public CommandManager(List<Class<?>> commandClasses, String command, JavaPlugin plugin) {
        this.plugin = plugin;
        this.mainCommand = command;
        locale = new Locale();
        this.commandValidator = new DefaultCommandValidator();

        for (Class clazz : commandClasses) {
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(Command.class)) continue;

                if (method.getParameters().length != 3) {
                    plugin.getLogger().warning("Method " + method.toGenericString().replace("public static void ", "") + " annotated as command but parameters count != 2");
                    continue;
                }
                if (method.getParameters()[0].getType() != CommandSender.class && method.getParameters()[0].getType() != Player.class) {
                    plugin.getLogger().warning("Method " + method.toGenericString().replace("public static void ", "") + " annotated as command but parameter 1's type != CommandSender || Player");
                    continue;
                }
                if (method.getParameters()[2].getType() != String[].class) {
                    plugin.getLogger().warning("Method " + method.toGenericString().replace("public static void ", "") + " annotated as command but parameter 3's type != String[]");
                    continue;
                }

                Command annotation = method.getAnnotation(Command.class);
                for (String commandName : annotation.aliases()) {
                    commands.put(commandName.toLowerCase(), method);
                }
            }
        }

        PluginCommand pluginCommand = plugin.getCommand(command);

        if (pluginCommand == null)
            throw new RuntimeException("The /" + command + " command doesn't exist in plugin.yml!");

        pluginCommand.setExecutor(new CommandExecutor(this, plugin));
        if (pluginCommand.getPlugin() != plugin) {
            throw new RuntimeException("/" + command + " command is being handled by plugin other than " + plugin.getDescription().getName() + ". You must use /" + plugin.getName().toLowerCase() + ":" + command + " instead.");
        }
    }

    public void setMainCommand(Class<?> command) {
        for (Method method : command.getMethods()) {
            if (!method.isAnnotationPresent(Command.class)) continue;

            if (method.getParameters().length != 3) {
                plugin.getLogger().warning("Method " + method.toGenericString().replace("public static void ", "") + " annotated as command but parameters count != 2");
                continue;
            }
            if (method.getParameters()[0].getType() != CommandSender.class && method.getParameters()[0].getType() != Player.class) {
                plugin.getLogger().warning("Method " + method.toGenericString().replace("public static void ", "") + " annotated as command but parameter 1's type != CommandSender || Player");
                continue;
            }
            if (method.getParameters()[2].getType() != String[].class) {
                plugin.getLogger().warning("Method " + method.toGenericString().replace("public static void ", "") + " annotated as command but parameter 3's type != String[]");
                continue;
            }
            mainCommandMethod = method;
        }
    }

    public boolean handle(CommandSender sender, String command, String[] args) {
        if (command == null) {
            Command commandAnnotation = mainCommandMethod.getAnnotation(Command.class);
            try {
                if (!commandValidator.canExecute(sender, commandAnnotation)) return true;

                if (mainCommandMethod.getParameters()[0].getType() == Player.class && !(sender instanceof Player)) {
                    sender.sendMessage(locale.getPlayerOnly());
                    return true;
                }

                mainCommandMethod.invoke(null, sender, plugin, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return true;
        }

        if (commands.containsKey(command.toLowerCase())) {
            try {
                Method commandMethod = commands.get(command.toLowerCase());
                Command commandAnnotation = commandMethod.getAnnotation(Command.class);

                if (!commandValidator.canExecute(sender, commandAnnotation)) return true;

                if (commandMethod.getParameters()[0].getType() == Player.class && !(sender instanceof Player)) {
                    sender.sendMessage(locale.getPlayerOnly());
                    return true;
                }

                if (commandAnnotation.requiredArgs() > args.length) {
                    sender.sendMessage(locale.getUsage("/" + mainCommand + " " + commandAnnotation.usage()));
                    return true;
                }

                commandMethod.invoke(null, sender, plugin, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            sender.sendMessage(getLocale().getUnknownCommand());
        }

        return true;
    }

    public class Locale {
        private String unknownCommand = "&cFailed to find that sub-command.";
        private String noPermission = "&cYou lack the permission to use this.";
        private String playerOnly = "&cThis command can only be executed in-game.";
        private String usage = "&7Usage: &f{usage}&7.";

        public String getUnknownCommand() {
            return StringUtil.translate(unknownCommand);
        }

        public void setUnknownCommand(String message) {
            this.unknownCommand = StringUtil.translate(message);
        }

        public String getNoPermission() {
            return StringUtil.translate(noPermission);
        }

        public void setNoPermission(String message) {
            this.noPermission = StringUtil.translate(message);
        }

        public String getPlayerOnly() {
            return StringUtil.translate(playerOnly);
        }

        public void setPlayerOnly(String message) {
            this.playerOnly = StringUtil.translate(message);
        }

        public String getUsage(String command) {
            return StringUtil.translate(usage.replace("{usage}", command));
        }

        public void setUsage(String message) {
            this.usage = StringUtil.translate(message);
        }
    }
}
