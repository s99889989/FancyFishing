package com.daxton.fancyfishing.listener;

import com.daxton.fancyfishing.fishing.Fishing;
import com.daxton.fancyfishing.fishing.real.FishingAction;
import com.daxton.fancyfishing.fishing.menu.FishingStartStop;
import com.daxton.fancyfishing.manager.Manager;
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


public class PlayerListener implements Listener {

    @EventHandler//當玩家登入
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        Manager.player_bag_size.put(uuidString, 18);
        Manager.player_item.put(uuidString, new ArrayList<>());
        Manager.fishing_Action_Map.put(uuidString, new FishingAction(player));
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
        String uuidString = player.getUniqueId().toString();

        Action action = event.getAction();

        Block block = event.getClickedBlock();
        if(action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR && equipmentSlot == EquipmentSlot.HAND){
            //右鍵開啟釣魚選單
            Fishing.Menu(player, itemStack);
        }
        if(action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR && equipmentSlot == EquipmentSlot.HAND){
            //左鍵直接開始釣魚
            Fishing.action(player, itemStack);
        }

    }


}
