package com.zerek.killdensemobs;

import com.zerek.killdensemobs.listeners.EntityTargetListener;
import com.zerek.killdensemobs.tasks.KillTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Collection;

public final class KillDenseMobs extends JavaPlugin {

    //field
    private Collection<LivingEntity> tempted = new ArrayList<LivingEntity>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EntityTargetListener(this),this);

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new KillTask(this), 0L, 200L);
    }

    @Override
    public void onDisable() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.cancelTasks(this);
        // Plugin shutdown logic
    }

    public Collection<LivingEntity> getTempted() {
        return tempted;
    }

//    public void setTempted(Collection<LivingEntity> tempted) {
//        this.tempted = tempted;
//    }
}
