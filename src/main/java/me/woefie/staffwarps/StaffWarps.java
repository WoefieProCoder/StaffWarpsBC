package me.woefie.staffwarps;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class StaffWarps extends JavaPlugin {

    public static StaffWarps instance;

    @Override
    public void onEnable() {
        instance = this;
        Objects.requireNonNull(this.getCommand("staffwarp")).setExecutor(new StaffWarpCommand());
        this.getCommand("staffwarp").setTabCompleter(new StaffWarpTabCompleter());
        this.saveDefaultConfig();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        this.saveDefaultConfig();
        // Plugin shutdown logic
    }
}
