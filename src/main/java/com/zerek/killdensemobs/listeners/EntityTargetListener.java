package com.zerek.killdensemobs.listeners;

import com.zerek.killdensemobs.KillDenseMobs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import java.util.ArrayList;
import java.util.Collection;

public class EntityTargetListener implements Listener {

    //field(s)
    private final KillDenseMobs plugin;

    //constructor
    public EntityTargetListener(KillDenseMobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityTrack(EntityTargetEvent event) {
        Collection<LivingEntity> tempted = this.plugin.getTempted();
        //plugin.getServer().broadcastMessage(event.getReason().toString());

        if (event.getReason().toString().equals("TEMPT"))
            tempted.add( (LivingEntity) event.getEntity());
    }
}
