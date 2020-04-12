package com.zerek.killdensemobs.tasks;

import com.zerek.killdensemobs.KillDenseMobs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import java.util.Collection;
import java.util.Random;
import java.util.Set;


public class KillTask implements Runnable {

    //field(s)
    private final KillDenseMobs plugin;

    //constructor
    public KillTask(KillDenseMobs plugin) {
        this.plugin = plugin;
    }

    //override run() from runnable
    @Override
    public void run()
    {
        //get tps
        double tps = plugin.getServer().getTPS()[0];

        //TPS minimum to kill
        if (tps <= 19.0)
        {
            //declare kill counter
            int killCount = 0;
            //create list "worlds" of all worlds on the server
            Collection<World> worlds = plugin.getServer().getWorlds();

            //iterate through all worlds
            for (World world : worlds)
            {
                //create list of "livingEntities" currently loaded
                Collection<LivingEntity> livingEntities = world.getLivingEntities();

                //keep entity on the list if it meets a case criteria
                livingEntities.removeIf(entity -> {
                    switch(entity.getType()){
                        case COW:
                        //case PIG:
                        //case CHICKEN:
                        //case RABBIT:
                        //case SHEEP:
                        //case SQUID:
                        //case ENDERMAN:
                        //case PIG_ZOMBIE:
                        //case CAVE_SPIDER:
                        case SPIDER:
                        //case BLAZE:
                        //case CREEPER:
                        //case DROWNED:
                        //case GHAST:
                        //case GUARDIAN:
                        //case PILLAGER:
                        case SKELETON:
                        //case SLIME:
                        //case VINDICATOR:
                        //case WITCH:
                        //case WITHER_SKELETON:
                        case ZOMBIE:

                            //keep on list
                            return false;

                        //all other types
                        default:break;
                    }
                    //remove from list
                    return true;
                });
                //check for name tag
                livingEntities.removeIf(entity ->(entity.getCustomName() != null));
                //check for helmet
                livingEntities.removeIf(entity ->(entity.getEquipment().getHelmet().getType() != Material.AIR));
                //check for chestplate
                livingEntities.removeIf(entity ->(entity.getEquipment().getChestplate().getType() != Material.AIR));
                //check for leggings
                livingEntities.removeIf(entity ->(entity.getEquipment().getLeggings().getType() != Material.AIR));
                //check for boots
                livingEntities.removeIf(entity ->(entity.getEquipment().getBoots().getType() != Material.AIR));
                //check for main hand
                livingEntities.removeIf(entity ->(entity.getEquipment().getItemInMainHand().getType() != Material.AIR));
                //check for off hand
                livingEntities.removeIf(entity ->(entity.getEquipment().getItemInOffHand().getType() != Material.AIR));
                //check for dense requirement
                livingEntities.removeIf(entity ->(entity.getNearbyEntities(3,4,3).size() < 50));

                //iterate through remaining livingEntities on the list
                for (LivingEntity livingEntity: livingEntities)
                {
                    //get 20% chance
                    Random rand = new Random();
                    int n = rand.nextInt(10);
                    if (n < 2)
                    {
                        //add to kill counter
                        killCount++;
                        //remove livingEntity
                        livingEntity.remove();
                    }
                }
            }
            //get list of ops
            Set<OfflinePlayer> ops = plugin.getServer().getOperators();
            //iterate through ops
            for (OfflinePlayer op : ops)
            {
                if (op.isOnline())
                {
                    //round tps to 2 decimal points
                    double roundedTPS = Math.round(tps * 100);
                    roundedTPS = roundedTPS/100;
                    //message op info
                    op.getPlayer().sendMessage(ChatColor.DARK_GRAY + "TPS: " + roundedTPS + " Dense Mobs Killed: " + killCount);
                }
            }
        }
    }
}
