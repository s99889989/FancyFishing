package com.daxton.fancyfishing.fishing.action;

import com.daxton.fancycore.api.aims.location.one.Look;
import com.daxton.fancycore.api.task.GuiseEntity;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.fishing.animation.OrbitalActionOut;
import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class FishingThrow {

    public static void execute(Player player){
        String uuidString = player.getUniqueId().toString();
        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);
        if(Manager.guise_Entity_Map.get(uuidString) == null){
            fishingMain.runAction("Throw-FishingRod-Start", player);
            //延遲15tick後甩竿
            BukkitRunnable bukkitRunnable2 = new BukkitRunnable() {
                @Override
                public void run() {
                    fishingMain.runAction("Throw-FishingRod-End", player);
                    GuiseEntity guiseEntity = GuiseEntity.createGuise( Look.getLook(player, 1));
                    guiseEntity.setVisible(true);
                    ItemStack hook = CustomItem.valueOf("FishingTools", "Buoy_Platinum_Star", 1);
                    if(fishingMain.fishHook != null){
                        hook = fishingMain.fishHook;
                    }
                    guiseEntity.appendItem16(hook, "HEAD");
                    Manager.guise_Entity_Map.put(uuidString, guiseEntity);
                    new OrbitalActionOut(uuidString, guiseEntity, guiseEntity.getLocation(), Look.getLook(player, 15).add(0,-4,0));
                }
            };
            bukkitRunnable2.runTaskLater(FancyFishing.fancyFishing, 15);
        }
    }

}
