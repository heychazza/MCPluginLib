# FeluxLib
This is a lightweight and easy to use library for fast plugin creation. I created this merely as a base for all my plugins, without the need to manually copy classes many times throughout each project.

## Features
Our library comes with a few useful features to help with the base of the plugin, we look to expand this as we move forward.

#### Command System
To use this, you'll want a base similar to below:
```java
public void registerCommands() {
    CommandManager commandManager = new CommandManager(Arrays.asList(ASubCommand.class), "maincommand", this);
    commandManager.setMainCommand(YourMainCommand.class);
    commandManager.getLocale().setNoPermission("&cYou cannot do that..");
    commandManager.getLocale().setUnknownCommand("&7Unknown, try /guitest help.");
    commandManager.getLocale().setUsage("&7Please use &b{usage}&7.");
    commandManager.getLocale().setPlayerOnly("&cConsole isn't currently supported.");
}
```

This allows for user configuration, for example to use the configuration file we would do:
```java
commandManager.getLocale().setNoPermission(getConfig().getString("messages.permission", "&cNo permission to do that."));
```

These can also be changed on the fly, and not just on boot as these values are always referenced in the command system.

