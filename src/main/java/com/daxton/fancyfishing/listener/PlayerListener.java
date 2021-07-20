package com.daxton.fancyfishing.listener;

import com.daxton.fancyfishing.api.FishingBag;
import com.daxton.fancyfishing.api.FishingGet;
import com.daxton.fancyfishing.api.FishingStartStop;
import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancycore.api.item.ItemKeySearch;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import com.daxton.fancycore.api.gui.GUI;

import java.util.ArrayList;
import java.util.List;


public class PlayerListener implements Listener {

    @EventHandler//當玩家登入
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        Manager.player_bag_size.put(uuidString, 18);
        Manager.player_item.put(uuidString, new ArrayList<>());
    }

    @EventHandler//當玩家移動
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        if(GUI.gui_Map.get(uuidString) != null){
            GUI gui = GUI.gui_Map.get(uuidString);

            FishingStartStop fishingStartStop = (FishingStartStop) gui.getAction(1, 5);
            if(fishingStartStop.fishing){
                fishingStartStop.stop();
            }

        }
    }

    //當玩家點擊時
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        EquipmentSlot equipmentSlot = event.getHand();

        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();


        Action action = event.getAction();
        Block block = event.getClickedBlock();
        if(action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR && equipmentSlot == EquipmentSlot.HAND){
            Fishing(player, itemStack);

        }

    }
    //開始釣魚
    public static void Fishing(Player player, ItemStack itemStack){
        String uuidString = player.getUniqueId().toString();
        String toolType = ItemKeySearch.getCustomAttributes(itemStack,"tooltype");
        boolean warterB = false;
        List<Block> los = player.getLineOfSight(null, 5);
        for (Block b : los) {
            if (b.getType() == Material.WATER) {
                warterB = true;
                break;
            }
        }
        if(warterB && toolType.equals("釣竿")){
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

}
