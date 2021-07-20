package com.daxton.fancyfishing.api;

import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import com.daxton.fancycore.api.gui.GuiAction;
import com.daxton.fancycore.api.gui.GUI;

import java.util.List;

public class FishingStartStop implements GuiAction{

    private final GUI gui;
    private final Player player;
    private final String uuidString;
    private BukkitRunnable bukkitRunnable;
    private BukkitRunnable countDown;
    public boolean fishing = false;

    public FishingStartStop(GUI gui, Player player){
        this.gui = gui;
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }

    public void execute(ClickType clickType){
        if(clickType == ClickType.LEFT){
            if(fishing){
                stop();
            }else {
                start();
            }
        }
    }
    //停止釣魚
    public void stop(){
        if(countDown != null){
            countDown.cancel();
        }
        if(bukkitRunnable != null){
            bukkitRunnable.cancel();
        }
        fishing = false;
        player.sendMessage("停止釣魚");
        gui.setItem(CustomItem.valueOf("FishingGui", "StartFishing", 1), false, 1,5);
        gui.removeItem(1,2);
        gui.removeItem(1,3);
    }
    //倒數
    public void count(){
        fishing = false;
        player.sendMessage("停止釣魚");
        gui.setItem(CustomItem.valueOf("FishingGui", "StartFishing", 1), false, 1,5);
        setItem();
        List<ItemStack> itemStacks = Manager.player_item.get(uuidString);
        int bagSize = Manager.player_bag_size.get(uuidString);
        if(itemStacks.size() < bagSize){
            countDown = new BukkitRunnable() {
                int i = 9;
                @Override
                public void run() {
                    if(i < 1){
                        cancel();
                        start();
                    }else {
                        gui.setItem(CustomItem.valueOf("FishingGui", String.valueOf(i--), 1), false, 1,3);
                    }

                }
            };
            countDown.runTaskTimer(FancyFishing.fancyFishing, 0 , 20);
        }else {
            player.sendMessage("背包滿了");
            gui.removeItem(1,2);
            gui.removeItem(1,3);
        }

    }
    //開始釣魚
    public void start(){
        List<ItemStack> itemStacks = Manager.player_item.get(uuidString);
        int bagSize = Manager.player_bag_size.get(uuidString);
        if(!fishing){
            if(itemStacks.size() < bagSize){
                if(countDown != null){
                    countDown.cancel();
                }
                if(bukkitRunnable != null){
                    bukkitRunnable.cancel();
                }
                fishing = true;
                player.sendMessage("開始釣魚");
                gui.setItem(CustomItem.valueOf("FishingGui", "StopFishing", 1), false, 1,5);
                gui.removeItem(1,2);
                gui.removeItem(1,3);
                bukkitRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        cancel();
                        count();
                    }
                };
                bukkitRunnable.runTaskLater(FancyFishing.fancyFishing, 20 * 5);
            }else {
                player.sendMessage("背包滿了");
                gui.removeItem(1,2);
                gui.removeItem(1,3);
            }

        }
    }

    public void setItem(){
        int max = 10;
        int min = 1;

        int i = (int) (Math.random() * (max - min + 1) + min);

        if(i < 5){
            List<ItemStack> itemStacks = Manager.player_item.get(uuidString);
            int bagSize = Manager.player_bag_size.get(uuidString);
            if(itemStacks.size() < bagSize){
                itemStacks.add(CustomItem.valueOf("Fish", "0", 1));
                gui.setItem(CustomItem.valueOf("Fish", "0", 1), false, 1,2);
                refreshBag();
            }
        }else {
            gui.setItem(CustomItem.valueOf("FishingGui", "FishingNoItem", 1), false, 1,2);
        }


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

}
