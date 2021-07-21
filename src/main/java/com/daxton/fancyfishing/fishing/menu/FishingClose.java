package com.daxton.fancyfishing.fishing.menu;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiCloseAction;
import org.bukkit.entity.Player;

public class FishingClose implements GuiCloseAction {

    private final GUI gui;
    private final Player player;
    private final String uuidString;

    public FishingClose(GUI gui, Player player){
        this.gui = gui;
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }

    public void execute(){

    }

}
