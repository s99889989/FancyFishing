package com.daxton.fancyfishing.fishing.action;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancyfishing.fishing.menu.MenuBag;
import com.daxton.fancyfishing.fishing.menu.MenuClose;
import com.daxton.fancyfishing.fishing.menu.MenuGetAllItem;
import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FishingMenu {

    public static void execute(Player player){
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(FishingMain.itemCheck(itemStack)){
            String uuidString = player.getUniqueId().toString();

            if(GUI.gui_Map.get(uuidString) == null){
                GUI gui = new GUI(9, "\uF808䀀");


                //gui.setItem(CustomItem.valueOf("FishingGui", "StartFishing", 1), false, 1,5);
                //gui.setAction(new FishingStartStop(gui, player), 1, 5);
                gui.setCloseAction(new MenuClose(gui, player));
                gui.setItem(CustomItem.valueOf("FishingGui", "FishingOpenBag", 1), false, 1,7);
                gui.setAction(new MenuBag(gui, player), 1, 7);
                gui.setItem(CustomItem.valueOf("FishingGui", "FishingItemGet", 1), false, 1,8);
                gui.setAction(new MenuGetAllItem(gui, player), 1, 8);
                //gui.setAllMove(false);
                gui.setMove(false, 1 ,2);
                GUI.gui_Map.put(uuidString, gui);
                gui.open(player);
            }else {
                GUI gui = GUI.gui_Map.get(uuidString);
                FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);
                fishingMain.refreshBag();
                gui.open(player);
            }
        }
    }

}
