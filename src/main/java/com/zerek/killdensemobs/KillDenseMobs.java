package com.zerek.killdensemobs;

import com.zerek.killdensemobs.tasks.KillTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class KillDenseMobs extends JavaPlugin {

    @Override
    public void onEnable() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new KillTask(this), 0L, 400L);
    }

    @Override
    public void onDisable() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.cancelTasks(this);
        // Plugin shutdown logic
    }
}
