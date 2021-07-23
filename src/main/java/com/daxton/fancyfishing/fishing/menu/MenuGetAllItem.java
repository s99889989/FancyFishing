package com.daxton.fancyfishing.fishing.menu;

import com.daxton.fancycore.api.item.ItemKeySearch;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.manager.Manager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiAction;

import java.util.*;

public class MenuGetAllItem implements GuiAction{

    private final GUI gui;
    private final Player player;
    private final String uuidString;

    public MenuGetAllItem(GUI gui, Player player){
        this.gui = gui;
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }

    public void execute(ClickType clickType, int slot){
        if(clickType == ClickType.LEFT){
            ItemStack[] itemStacks = Manager.player_item.get(uuidString);

            Arrays.stream(itemStacks).filter(Objects::nonNull).forEach(itemStack -> {
                player.getInventory().addItem(itemStack);
                itemStacks[Arrays.asList(itemStacks).indexOf(itemStack)]=null;
            });
            refreshBag();
        }
    }

    //刷新背包
    public void refreshBag(){
        ItemStack[] itemStacks = Manager.player_item.get(uuidString);
        int i = 10;
        gui.clearFrom(10);
        for(ItemStack itemStack : itemStacks){
            if(itemStack != null){
                gui.setItem(itemStack, false, i);
            }
            i++;
        }
    }

}
