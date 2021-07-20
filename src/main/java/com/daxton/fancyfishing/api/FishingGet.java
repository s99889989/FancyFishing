package com.daxton.fancyfishing.api;

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

public class FishingGet implements GuiAction{

    private final GUI gui;
    private final Player player;
    private final String uuidString;

    public  FishingGet(GUI gui, Player player){
        this.gui = gui;
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }

    public void execute(ClickType clickType){
        if(clickType == ClickType.LEFT){
            List<ItemStack> itemStacks = Manager.player_item.get(uuidString);

            Map<String, Integer> item_amount_Map = new HashMap<>();
            Map<String, ItemStack> item_stack_Map = new HashMap<>();
            if(!itemStacks.isEmpty()){
                itemStacks.forEach(itemStack -> {
                    String itemID = itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(FancyFishing.fancyFishing, "itemID"), PersistentDataType.STRING);
                    if(item_amount_Map.get(itemID) != null){
                        int amount = item_amount_Map.get(itemID) + 1;
                        item_amount_Map.put(itemID, amount);
                    }
                    item_amount_Map.putIfAbsent(itemID, 1);
                    item_stack_Map.putIfAbsent(itemID, itemStack);
                });
            }

            item_stack_Map.forEach((s, itemStack) -> {
                int amount = item_amount_Map.get(s);
                itemStack.setAmount(amount);

                player.getInventory().addItem(itemStack);
                itemStacks.clear();

                refreshBag();
            });


        }

    }

    //刷新背包
    public void refreshBag(){
        List<ItemStack> itemStacks = Manager.player_item.get(uuidString);
        int i = 10;
        gui.clearFrom(10);
        for(ItemStack itemStack : itemStacks){
            gui.addItem(itemStack, false, i);
            i++;
        }
    }

}
