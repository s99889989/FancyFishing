package com.daxton.fancyfishing.fishing.action;

import com.daxton.fancycore.api.aims.location.one.Look;
import com.daxton.fancycore.api.task.GuiseEntity;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.fishing.animation.OrbitalActionOut;
import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FishingCount {

    public static void execute(Player player){
        String uuidString = player.getUniqueId().toString();
        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);

        fishingMain.bukkitRunnable = new BukkitRunnable() {
            int i = 9;
            @Override
            public void run() {
                if(i < 1){
                    cancel();
                    fishingMain.executeFirst();
                }else {
                    player.sendTitle(" ",i+"秒後重新開始釣魚",10,20,5);
                    i--;
                }

            }
        };
        fishingMain.bukkitRunnable.runTaskTimer(FancyFishing.fancyFishing, 0 , 20);
    }

}
