package com.daxton.fancyfishing.api;

import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import com.daxton.fancycore.api.gui.GuiAction;
import com.daxton.fancycore.api.gui.GUI;

import java.util.List;

public class FishingBag implements GuiAction{

    private final GUI gui;
    private final Player player;
    private final String uuidString;
    private boolean state = false;

    public FishingBag(GUI gui, Player player){
        this.gui = gui;
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }

    public void execute(ClickType clickType){
        if(clickType == ClickType.LEFT){
            if(state){
                close();
            }else {
                open();
            }
        }
    }

    public void open(){
        state = true;
        gui.setItem(CustomItem.valueOf("FishingGui", "FishingCloseBag", 1), false, 1,7);
        gui.setTitle("\uF808䀁");
        gui.setSize(54);
        refreshBag();
        gui.open(player);
    }

    //刷新背包
    public void refreshBag(){
        List<ItemStack> itemStacks = Manager.player_item.get(uuidString);
        int i = 10;
        for(ItemStack itemStack : itemStacks){
            gui.addItem(itemStack, false, i);
            i++;
        }
    }

    public void close(){
        state = false;
        gui.setItem(CustomItem.valueOf("FishingGui", "FishingOpenBag", 1), false, 1,7);
        //gui.clearFrom(10);
        gui.setTitle("\uF808䀀");
        gui.setSize(9);
        gui.open(player);
    }

}
