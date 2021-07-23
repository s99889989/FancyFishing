package com.daxton.fancyfishing.fishing.menu;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiCloseAction;
import com.daxton.fancyfishing.fishing.action.FishingMain;
import com.daxton.fancyfishing.manager.Manager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MenuClose implements GuiCloseAction {

    private final GUI gui;
    private final Player player;
    private final String uuidString;

    public MenuClose(GUI gui, Player player){
        this.gui = gui;
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }

    public void execute(){
        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);
        ItemStack itemStack = gui.getItem(1, 2);
        if(itemStack != null){
            fishingMain.fishHook = itemStack;
        }


    }

}
