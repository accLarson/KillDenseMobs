package com.zerek.killdensemobs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import java.util.*;

public final class KillDenseMobs extends JavaPlugin {

    @Override
    public void onEnable() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable()
        {
            @Override
            public void run()
            {
                double tps = getServer().getTPS()[0];

                //TPS minimum to kill
                if (tps <= 19.0)
                {
                    int killCount = 0;
                    Collection<World> worlds = getServer().getWorlds();
                    for (World world : worlds)
                    {
                        Collection<LivingEntity> livingEntities = world.getLivingEntities();
                        livingEntities.removeIf(entity -> {
                            switch(entity.getType()){
                                case COW:
                                case PIG:
                                case CHICKEN:
                                //case RABBIT:
                                case SHEEP:
                                case SQUID:
                                case ENDERMAN:
                                case PIG_ZOMBIE:
                                case CAVE_SPIDER:
                                case SPIDER:
                                case BLAZE:
                                case CREEPER:
                                case DROWNED:
                                case GHAST:
                                case GUARDIAN:
                                case PILLAGER:
                                case SKELETON:
                                case SLIME:
                                case VINDICATOR:
                                case WITCH:
                                case WITHER_SKELETON:
                                case ZOMBIE:

                                    return false;
                                default:break;
                            }
                            return true;
                        });
                        livingEntities.removeIf(entity ->(entity.getCustomName() != null));
                        livingEntities.removeIf(entity ->(entity.getEquipment().getHelmet().getType() != Material.AIR));
                        livingEntities.removeIf(entity ->(entity.getEquipment().getChestplate().getType() != Material.AIR));
                        livingEntities.removeIf(entity ->(entity.getEquipment().getLeggings().getType() != Material.AIR));
                        livingEntities.removeIf(entity ->(entity.getEquipment().getBoots().getType() != Material.AIR));
                        livingEntities.removeIf(entity ->(entity.getEquipment().getItemInMainHand().getType() != Material.AIR));
                        livingEntities.removeIf(entity ->(entity.getEquipment().getItemInOffHand().getType() != Material.AIR));
                        livingEntities.removeIf(entity ->(entity.getNearbyEntities(3,4,3).size() < 50));
                        for (LivingEntity livingEntity: livingEntities)
                        {
                            Random rand = new Random();
                            int n = rand.nextInt(10);
                            if (n < 2)
                            {
                                killCount++;
                                livingEntity.remove();
                            }
                            //getServer().broadcastMessage(String.valueOf(livingEntity.getEquipment().getItemInMainHand().getType()));
                        }
                    }
                    Set<OfflinePlayer> ops = getServer().getOperators();
                    for (OfflinePlayer op : ops)
                    {
                        if (op.isOnline())
                        {
                            //String roundedTPS = String.format("%.2f%n", tps);
                            op.getPlayer().sendMessage(ChatColor.DARK_GRAY + "TPS: " + tps + "\nKilled " + killCount + " Dense Mobs");
                        }

                    }
                }
            }
        }, 0L, 400L);
    }
    @Override
    public void onDisable() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.cancelTasks(this);
        // Plugin shutdown logic
    }
}
