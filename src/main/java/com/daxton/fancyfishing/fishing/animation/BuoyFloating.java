package com.daxton.fancyfishing.fishing.animation;

import com.daxton.fancycore.api.task.GuiseEntity;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.manager.Manager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class BuoyFloating extends BukkitRunnable {

    private boolean b = false;

    public BuoyFloating(){


        runTaskTimer(FancyFishing.fancyFishing, 0L, 10);
    }

    public void run(){

        if(b){
            Bukkit.getOnlinePlayers().forEach(player -> {
                String uuidString = player.getUniqueId().toString();
                if(Manager.guise_Entity_Map.get(uuidString) != null){
                    GuiseEntity guiseEntity = Manager.guise_Entity_Map.get(uuidString);
                    Location location = guiseEntity.getLocation().clone();
                    for(int i = 0 ; i < 5 ;i++){
                        location.add(0,0.01*i,0);
                        guiseEntity.teleport(location);
                    }
                }

            });

            b = false;
        }else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                String uuidString = player.getUniqueId().toString();
                if(Manager.guise_Entity_Map.get(uuidString) != null){
                    GuiseEntity guiseEntity = Manager.guise_Entity_Map.get(uuidString);
                    Location location = guiseEntity.getLocation().clone();
                    for(int i = 0 ; i < 5 ;i++){
                        location.add(0,-0.01*i,0);
                        guiseEntity.teleport(location);
                    }
                }

            });
            b = true;
        }


    }

}
