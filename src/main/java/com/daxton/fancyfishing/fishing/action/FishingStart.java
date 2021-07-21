package com.daxton.fancyfishing.fishing.action;

import com.daxton.fancycore.api.aims.location.one.Look;
import com.daxton.fancycore.api.task.GuiseEntity;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.fishing.animation.OrbitalActionStop;
import com.daxton.fancyfishing.manager.Manager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FishingStart {

    public static void execute(Player player){
        String uuidString = player.getUniqueId().toString();
        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);
        fishingMain.locationPlayer = player.getLocation();
        fishingMain.runAction("Buoy-Water", player);
        fishingMain.isFishing = true;
        fishingMain.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                cancel();
                //釣魚時間到
                //收回魚誘
                if(Manager.guise_Entity_Map.get(uuidString) != null){
                    GuiseEntity guiseEntity2 = Manager.guise_Entity_Map.get(uuidString);
                    new OrbitalActionStop(uuidString, guiseEntity2, guiseEntity2.getLocation(), Look.getLook(player, 2));
                }

                //檢查是否掉到東西
                fishingMain.catchItem();
            }
        };
        fishingMain.bukkitRunnable.runTaskLater(FancyFishing.fancyFishing, 20 * 5);
    }

}
