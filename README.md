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

Inside of `YourMainCommand.class` you'll want to have:
```java
public class YourMainCommand {
    @Command(permission = "feluxlib.maincmd")
    public static void execute(final CommandSender sender, final YourPlugin plugin, final String[] args) {
        // Your code here.
    }
}
```

Then inside of a sub-command, such as `ASubCommand.class` you'll want:
```java
public class ASubCommand {
    @Command(permission = "feluxlib.sub", usage = "say", aliases = {"say"}, requiredArgs = 1)
    public static void execute(final CommandSender sender, final FeluxPlugin plugin, final String[] args) {
        String message = args[0];
        Bukkit.getServer().broadcastMessage(StringUtil.translate("&c[Broadcast] &7" + message));
    }
}
```

This allows for user configuration, for example to use the configuration file we would do:
```java
commandManager.getLocale().setNoPermission(getConfig().getString("messages.permission", "&cNo permission to do that."));
```

These can also be changed on the fly, and not just on boot as these values are always referenced in the command system.

## Items
Our library provides an easy way to quickly and easily create itemstacks on the fly. For example, if you wanted to create a diamond sword that has a name, lore, enchants, item flags and nbt information:
```java
        // To build the item.
        ItemBuilder swordBuilder = new ItemBuilder(Material.DIAMOND_SWORD)
                .withName("&cLucky Sword")
                .withLore("&7A powerful, strong sword.")
                .withEnchant(Enchantment.KNOCKBACK, 1)
                .withFlag(ItemFlag.HIDE_ENCHANTS)
                .withData(0)
                .withNBTString("sword-type", "strong");

        // To get the itemstack itself.
        ItemStack swordItem = swordBuilder.getItem();
```

As you see, this can be easily done on the fly and could easily use configuration values instead (e.g. a voucher item).

#### NBT Data
As you see in the above, we set the NBT tag key `sword-type` which in our case goes to the value `strong`. This is useful for checking if the player interacted with our item, due to its unique nbt tag. We can check if its our sword by the following code:
```java
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        String swordType = ItemUtil.getNBTString(item, "sword-type");

        if (swordType != null && swordType.equalsIgnoreCase("strong"))
            e.getPlayer().sendMessage("You clicked the strong sword.");
    }
```
