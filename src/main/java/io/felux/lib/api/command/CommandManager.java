package io.felux.lib.api.command;

import io.felux.lib.api.general.StringUtil;
import org.bukkit.command.CommandSender;
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
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Map<String, Method> getCommands() {
        return commands;
    }

    public CommandManager(List<Class<?>> commandClasses, String command, JavaPlugin plugin) {
        this.plugin = plugin;
        this.mainCommand = command;
        this.locale = new Locale();

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
        plugin.getCommand(command).setExecutor(new CommandExecutor(this, plugin));
        if (plugin.getCommand(command).getPlugin() != plugin) {
            plugin.getLogger().warning("/" + command + " command is being handled by plugin other than " + plugin.getDescription().getName() + ". You must use /" + plugin.getName().toLowerCase() + ":" + command + " instead.");
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
            try {
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

                if (!sender.hasPermission(commandAnnotation.permission())) {
                    sender.sendMessage(locale.getNoPermission());
                    return true;
                }

                if (commandMethod.getParameters()[0].getType() == Player.class && !(sender instanceof Player)) {
                    sender.sendMessage(locale.getPlayerOnly());
                    return true;
                }

                if (commandAnnotation.requiredArgs() > args.length) {
                    sender.sendMessage(locale.getUsage(command + commandAnnotation.usage()));
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
        private String usage = "&7Usage: &f/{usage}&7.";

        public String getUnknownCommand() {
            return unknownCommand;
        }

        public void setUnknownCommand(String message) {
            this.unknownCommand = StringUtil.translate(message);
        }

        public String getNoPermission() {
            return noPermission;
        }

        public void setNoPermission(String message) {
            this.noPermission = StringUtil.translate(message);
        }

        public String getPlayerOnly() {
            return playerOnly;
        }

        public void setPlayerOnly(String message) {
            this.playerOnly = StringUtil.translate(message);
        }

        public String getUsage(String command) {
            return usage.replace("{usage}", command);
        }

        public void setUsage(String message) {
            this.usage = StringUtil.translate(message);
        }
    }
}
