package com.daxton.fancyfishing.fishing.action;

import com.daxton.fancycore.api.aims.location.one.Look;
import com.daxton.fancycore.api.task.GuiseEntity;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.fishing.animation.GetItem;
import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class FishBait {

    public static void execute(Player player){
        String uuidString = player.getUniqueId().toString();
        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);

        int max = 10;
        int min = 1;

        int i = (int) (Math.random() * (max - min + 1) + min);

        if(i < 8){

            if(fishingMain.checkBagFull()){
                List<ItemStack> itemStacks = Manager.player_item.get(uuidString);

                ItemStack itemStack = CustomItem.valueOf("Fish", "0", 1);
                itemStacks.add(itemStack);

                fishingMain.runAction("Caught-Item", player);
                player.sendTitle(" ","釣到了"+itemStack.getItemMeta().getDisplayName(),10,40,40);

                GuiseEntity guiseEntity = GuiseEntity.createGuise( Look.getLook(player, 1));
                guiseEntity.setVisible(true);
                guiseEntity.appendItem16(itemStack, "HEAD");
                new GetItem(uuidString, guiseEntity, fishingMain.buoy, Look.getLook(player, 3).add(0,-2,0));
            }
        }else {
            //ItemStack itemStack = CustomItem.valueOf("FishingGui", "FishingNoItem", 1);
            //player.sendTitle(" ",itemStack.getItemMeta().getDisplayName(),10,40,40);
            fishingMain.runAction("Caught-No-Item", player);

        }
        fishingMain.isFishing = false;
        fishingMain.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                cancel();
                fishingMain.readyStart();
            }
        };
        fishingMain.bukkitRunnable.runTaskLater(FancyFishing.fancyFishing, 15);
    }

}
