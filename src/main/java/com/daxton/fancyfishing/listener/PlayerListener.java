package com.daxton.fancyfishing.listener;

import com.daxton.fancycore.api.aims.entity.judgment.Range;
import com.daxton.fancycore.api.item.ItemKeySearch;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.fishing.action.FishingMain;
import com.daxton.fancyfishing.manager.Manager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import com.daxton.fancycore.api.gui.GUI;

import java.util.ArrayList;


public class PlayerListener implements Listener {

    public static int ii;

    @EventHandler//當玩家登入
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        Manager.player_item.put(uuidString, new ItemStack[18]);

        Manager.fishing_Main_Map.put(uuidString, new FishingMain(player));
    }

    @EventHandler//當玩家移動
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);
        if(fishingMain.isFishing){
            if(!Range.isIn(fishingMain.locationPlayer, player, 2)){
                fishingMain.stop();
            }
        }

    }

    //當玩家點擊時
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        EquipmentSlot equipmentSlot = event.getHand();

        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();

        Action action = event.getAction();

        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);

        if(action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR && equipmentSlot == EquipmentSlot.HAND){
            //右鍵開啟釣魚選單
            fishingMain.openMenu();
        }
        if(action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR && equipmentSlot == EquipmentSlot.HAND){
            //左鍵直接開始釣魚
            fishingMain.leftClick();
        }

    }
    //當使用背包時
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player player)){
            return;
        }

        String uuidString = player.getUniqueId().toString();
        InventoryAction action = event.getAction();
        int slot = event.getSlot();
        //FancyFishing.fancyFishing.getLogger().info(slot+" : "+action);

        if(slot != 1 && action == InventoryAction.PICKUP_ALL){
            ItemStack itemStack = player.getInventory().getItem(slot);
            if(itemStack != null){
                ii = slot;
                if(GUI.gui_Map.get(uuidString) != null){
                    GUI gui = GUI.gui_Map.get(uuidString);
                    gui.setMove(itemCheck(itemStack), 1, 2);
                }
            }

        }

        if(slot == 1){
            ItemStack itemStack = event.getInventory().getItem(1);
            if(itemStack != null && !itemCheck(itemStack)){
                //FancyFishing.fancyFishing.getLogger().info(itemStack.getType().toString());
                event.getInventory().remove(itemStack);
                player.getInventory().setItem(ii, itemStack);
            }

        }

    }

    //檢查是否為釣竿
    public static boolean itemCheck(ItemStack itemStack){
        boolean output = false;
        if(itemStack.getType() != Material.AIR){
//            if(itemStack.getItemMeta() != null){
//                //String dn = itemStack.getItemMeta().getDisplayName();
//                //FancyFishing.fancyFishing.getLogger().info(dn);
//            }

            String toolType = ItemKeySearch.getCustomAttributes(itemStack,"tooltype");
            //FancyFishing.fancyFishing.getLogger().info(toolType);
            if(toolType.equals("浮標")){
                output = true;
            }
        }
        return output;
    }


}
