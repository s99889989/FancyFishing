package com.daxton.fancyfishing.fishing.action;

import com.daxton.fancycore.api.aims.location.one.Look;
import com.daxton.fancycore.api.item.ItemKeySearch;
import com.daxton.fancycore.api.task.GuiseEntity;
import com.daxton.fancycore.api.taskaction.StringToMap;
import com.daxton.fancycore.task.TaskAction;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.config.FileConfig;
import com.daxton.fancyfishing.fishing.FishingStatus;
import com.daxton.fancyfishing.fishing.animation.OrbitalActionStop;
import com.daxton.fancyfishing.manager.Manager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;

public class FishingMain {

    private final Player player;
    private final String uuidString;
    public BukkitRunnable bukkitRunnable;
    public Location buoy;
    public Location locationPlayer;
    public boolean isFishing = false;
    public ItemStack fishHook;

    public FishingMain(Player player){
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }
    //打開釣魚選單
    public void openMenu(){
        FishingMenu.execute(player);
    }

    //左鍵
    public void leftClick(){
        if(isFishing){
            stop();
        }else {
            if(bukkitRunnable != null && !bukkitRunnable.isCancelled()){
                bukkitRunnable.cancel();
            }
            executeFirst();
        }
    }

    //甩竿動作
    public void executeFirst(){
        if(checkBagFull())
        FishingThrow.execute(player);
    }
    //倒數準備開始甩竿
    public void readyStart(){
        if(checkBagFull())
        FishingCount.execute(player);
    }
    //檢查是否掉到東西
    public void catchItem(){
        FishBait.execute(player);
    }
    //執行
    public void execute(FishingStatus fishingStatus){
        if(fishingStatus == FishingStatus.WATER){
            FishingStart.execute(player);
        }
        if(fishingStatus == FishingStatus.PLAYER){
            stop();
        }
    }
    //停止釣魚
    public void stop(){
        if(isFishing){
            bukkitRunnable.cancel();
            runAction("Fishing-Stop", player);
            if(Manager.guise_Entity_Map.get(uuidString) != null){
                GuiseEntity guiseEntity = Manager.guise_Entity_Map.get(uuidString);
                new OrbitalActionStop(uuidString, guiseEntity, guiseEntity.getLocation(), Look.getLook(player, 2));
            }
            isFishing = false;
        }
    }



    //執行動作
    public void runAction(String actionKey, LivingEntity self){

        FileConfiguration config = FileConfig.config_Map.get("config.yml");
        List<String> actionStringList = config.getStringList(actionKey+".Action");

        List<Map<String, String>> actionMapList = StringToMap.toActiomMapList(actionStringList);
        actionMapList.forEach(stringStringMap -> {
            TaskAction.execute(self, null, stringStringMap, String.valueOf(Math.random() * Integer.MAX_VALUE), null);
        });
    }

    //確認背包是否滿了，滿了就回傳false
    public boolean checkBagFull(){
        List<ItemStack> itemStacks = Manager.player_item.get(uuidString);
        int bagSize = Manager.player_bag_size.get(uuidString);
        if(itemStacks.size() >= bagSize){
            runAction("Bag-Full", player);
        }
        return itemStacks.size() < bagSize;
    }

    //檢查手上物品是否是釣竿
    public static boolean itemCheck(ItemStack itemStack){
        boolean output = false;
        String toolType = ItemKeySearch.getCustomAttributes(itemStack,"tooltype");
        //FancyFishing.fancyFishing.getLogger().info(toolType);
        if(toolType.equals("釣竿")){
            output = true;
        }
        return output;
    }
    //檢查是否看到水
    public static boolean isLookWater(Player player){
        boolean output = false;
        List<Block> los = player.getLineOfSight(null, 5);
        for (Block b : los) {
            if (b.getType() == Material.WATER) {
                output = true;
                break;
            }
        }
        return output;
    }


    public Player getPlayer() {
        return player;
    }
}
