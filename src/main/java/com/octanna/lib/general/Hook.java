package com.octanna.lib.general;

import org.bukkit.Bukkit;

public abstract class Hook {

    public String getPlugin() {
        return "";
    }

    public boolean isEnabled() {
        return Bukkit.getPluginManager().getPlugin(getPlugin()) != null;
    }

    public void register() {
        if (isEnabled()) onEnable();
    }

    public void onEnable() {
    }
}