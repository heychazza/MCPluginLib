package dev.chapi.test;

import dev.chapi.api.item.ItemBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public class Chapi extends JavaPlugin {

    @Override
    public void onEnable() {
        new ItemBuilder("DIRT", 1);
    }
}
