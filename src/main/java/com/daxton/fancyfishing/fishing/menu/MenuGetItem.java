package com.daxton.fancyfishing.fishing.menu;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class MenuGetItem implements GuiAction {

    private final GUI gui;
    private final Player player;
    private final String uuidString;

    public MenuGetItem(GUI gui, Player player){
        this.gui = gui;
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }

    public void execute(ClickType clickType, int slot){
        if(clickType == ClickType.LEFT){

        }
    }



}
