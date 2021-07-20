package com.daxton.fancyfishing.fishing;


import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.item.ItemKeySearch;
import com.daxton.fancycore.api.task.location.GuiseEntity;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.fishing.menu.FishingBag;
import com.daxton.fancyfishing.fishing.menu.FishingGet;
import com.daxton.fancyfishing.fishing.menu.FishingStartStop;
import com.daxton.fancyfishing.fishing.real.OrbitalActionIn;
import com.daxton.fancyfishing.fishing.real.OrbitalActionOut;
import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class Fishing {

    //開啟釣魚選單
    public static void Menu(Player player, ItemStack itemStack){
        if(itemCheck(itemStack) && isLookWater(player)){
            String uuidString = player.getUniqueId().toString();
            GUI gui = new GUI(9, "\uF808䀀");
            GUI.gui_Map.put(uuidString, gui);
            gui.setItem(CustomItem.valueOf("FishingGui", "StartFishing", 1), false, 1,5);
            gui.setItem(CustomItem.valueOf("FishingGui", "FishingOpenBag", 1), false, 1,7);
            gui.setItem(CustomItem.valueOf("FishingGui", "FishingItemGet", 1), false, 1,8);
            gui.setAction(new FishingStartStop(gui, player), 1, 5);
            gui.setAction(new FishingBag(gui, player), 1, 7);
            gui.setAction(new FishingGet(gui, player), 1, 8);
            gui.setAllMove(false);
            gui.open(player);
        }
    }
    //直接開始釣魚
    public static void action(Player player, ItemStack itemStack){
        if(itemCheck(itemStack)){
            String uuidString = player.getUniqueId().toString();
            if(Manager.guise_Entity_Map.get(uuidString) == null){
                player.getLocation().getWorld().playSound(player.getLocation(), "getamped_fishing_throw", 1, 1);
                BukkitRunnable bukkitRunnable2 = new BukkitRunnable() {
                    @Override
                    public void run() {
                        GuiseEntity guiseEntity = GuiseEntity.createGuise( getLook(player, 1));
                        guiseEntity.setVisible(true);
                        guiseEntity.appendItem16(CustomItem.valueOf("FishingTools", "buoy", 1), "HEAD");
                        Manager.guise_Entity_Map.put(uuidString, guiseEntity);
                        new OrbitalActionOut(uuidString, guiseEntity, guiseEntity.getLocation(), getLook(player, 15).add(0,-3,0));
                    }
                };
                bukkitRunnable2.runTaskLater(FancyFishing.fancyFishing, 15);
            }else {
                GuiseEntity guiseEntity = Manager.guise_Entity_Map.get(uuidString);

                new OrbitalActionIn(uuidString, guiseEntity, guiseEntity.getLocation(), getLook(player, 2));
            }


        }
    }



    public static Location getLook(LivingEntity self, int distance){

        Location location = self.getLocation();
        Vector vector = location.getDirection().multiply(distance);
        location.add(vector);

        return location;
    }
    //檢查手上物品是否是釣竿
    public static boolean itemCheck(ItemStack itemStack){
        boolean output = false;
        String toolType = ItemKeySearch.getCustomAttributes(itemStack,"tooltype");
        //FancyFishing.fancyFishing.getLogger().info(toolType);
        if(toolType.equals("釣竿")){
            output = true;
        }
        return output;
    }
    //檢查是否看到水
    public static boolean isLookWater(Player player){
        boolean output = false;
        List<Block> los = player.getLineOfSight(null, 5);
        for (Block b : los) {
            if (b.getType() == Material.WATER) {
                output = true;
                break;
            }
        }
        return output;
    }

}
