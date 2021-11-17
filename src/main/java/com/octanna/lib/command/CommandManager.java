package com.octanna.lib.command;

import com.octanna.lib.command.defaults.DefaultCommandValidator;
import com.octanna.lib.general.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CommandManager {

    private JavaPlugin plugin;
    private Map<String, Method> commands = new HashMap<>();

    private String mainCommand;
    private List<String> aliases;
    private Method mainCommandMethod;
    private Locale locale;
    private CommandValidator commandValidator;

    private boolean mainCommandArgs = false;

    public PluginCommand createCommand() {
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            PluginCommand cmd = c.newInstance(mainCommand, plugin);
            cmd.setDescription("Manage players' shops or this plugin.");
            cmd.setUsage("/" + mainCommand);
            cmd.setAliases(getAliases());
            cmd.setExecutor(new CommandExecutor(this, plugin));
//            cmd.setTabCompleter(new ShopBaseTabCompleter());

            return cmd;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            plugin.getLogger().severe("Failed to create command");
        }

        return null;
    }

    public void register() {
        try {
            Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);

            Object commandMapObject = f.get(Bukkit.getPluginManager());
            if (commandMapObject instanceof CommandMap) {
                CommandMap commandMap = (CommandMap) commandMapObject;
                commandMap.register(plugin.getName(), createCommand());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            plugin.getLogger().severe("Failed to register command");
        }
    }

    public CommandManager(List<Class<?>> commandClasses, String command, JavaPlugin plugin) {
        this.plugin = plugin;
        this.mainCommand = command;
        this.aliases = new ArrayList<>();
        this.locale = new Locale();
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

        addAlias(mainCommand);
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void addAlias(String alias) {
        aliases.add(alias);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Map<String, Method> getCommands() {
        return commands;
    }

    public void setCommandValidator(CommandValidator commandValidator) {
        this.commandValidator = commandValidator;
    }

    public boolean useMainCommandForArgs() {
        return mainCommandArgs;
    }

    public void setMainCommandArgs(final boolean mainCommandArgs) {
        this.mainCommandArgs = mainCommandArgs;
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
                if (!commandValidator.canExecute(sender, commandAnnotation, this)) return true;

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

                if (!commandValidator.canExecute(sender, commandAnnotation, this)) return true;

                if (commandMethod.getParameters()[0].getType() == Player.class && !(sender instanceof Player)) {
                    sender.sendMessage(locale.getPlayerOnly());
                    return true;
                }

                if (commandAnnotation.requiredArgs() > args.length) {
                    sender.sendMessage(locale.getUsage(commandAnnotation.aliases()[0], commandAnnotation.usage()));
                    return true;
                }

                commandMethod.invoke(null, sender, plugin, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            if (useMainCommandForArgs()) {
                Command commandAnnotation = mainCommandMethod.getAnnotation(Command.class);
                try {
                    if (!commandValidator.canExecute(sender, commandAnnotation, this)) return true;

                    if (mainCommandMethod.getParameters()[0].getType() == Player.class && !(sender instanceof Player)) {
                        sender.sendMessage(locale.getPlayerOnly());
                        return true;
                    }

                    List<String> cmdArgs = new ArrayList<>();
                    cmdArgs.add(command);
                    Collections.addAll(cmdArgs, args);

                    mainCommandMethod.invoke(null, sender, plugin, cmdArgs.toArray(new String[0]));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return true;
            }
            sender.sendMessage(getLocale().getUnknownCommand());
        }

        return true;
    }

    public class Locale {
        private String unknownCommand = "&cFailed to find that sub-command.";
        private String noPermission = "&cYou lack the permission to use this.";
        private String playerOnly = "&cThis command can only be executed in-game.";
        private String usage = "&7Usage: &f/{command} {subcommand} &7{usage}&7.";

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

        public String getUsage(String subCommand, String cmdUsage) {
            String usageStr = usage.replace("{command}", mainCommand)
                    .replace("{subcommand}", subCommand)
                    .replace("{usage}", cmdUsage);

            return StringUtil.translate(usageStr);
        }

        public void setUsage(String message) {
            this.usage = StringUtil.translate(message);
        }
    }
}
