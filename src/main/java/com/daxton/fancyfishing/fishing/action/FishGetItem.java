package com.daxton.fancyfishing.fishing.action;

import com.daxton.fancycore.api.aims.location.one.Look;
import com.daxton.fancycore.api.item.CItem;
import com.daxton.fancycore.api.judgment.NumberJudgment;
import com.daxton.fancycore.api.task.GuiseEntity;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.config.FileConfig;
import com.daxton.fancyfishing.fishing.ItemChangeSet;
import com.daxton.fancyfishing.fishing.animation.GetItem;
import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class FishGetItem {

    public static Map<String, Integer> itemChangeMap = new HashMap<>();

    //丟入物品字串列表，獲取整個列表加總機率。
    public static int getAllItemChange(List<String> itemStringList){
        int count = 0;
        for(String itemString : itemStringList){
            count += getItemChange(itemString);
        }
        return count;
    }

    //丟入物品字串，獲取該物品機率。
    public static int getItemChange(String itemString){
        int count = 0;
        String[] itemStringArray = itemString.split(" ");
        if(itemStringArray.length == 3 && NumberJudgment.isNumber(itemStringArray[1])){
            count = Integer.parseInt(itemStringArray[1]);
        }
        if(itemStringArray.length == 2 && NumberJudgment.isNumber(itemStringArray[1])){
            count = Integer.parseInt(itemStringArray[1]);
        }
        if(itemStringArray.length == 1){
            count = 1;
        }
        return count;
    }

    public static void execute(Player player){
        String uuidString = player.getUniqueId().toString();
        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);

        if(FileConfig.config_Map.get("Items.yml") != null){
            FileConfiguration itemConfig = FileConfig.config_Map.get("Items.yml");
            List<String> itemStringList = itemConfig.getStringList("ItemList");
            AllChange(player, itemConfig, itemStringList);
        }

        fishingMain.isFishing = false;
        fishingMain.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                cancel();
                fishingMain.readyStart();
            }
        };
        fishingMain.bukkitRunnable.runTaskLater(FancyFishing.fancyFishing, 15);
    }

    //機率分開算可以獲得多個
    public static void OneChange(Player player, FileConfiguration itemConfig, List<String> itemStringList){
        String uuidString = player.getUniqueId().toString();
        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);



    }

    //全部物品統一機率
    public static void AllChange(Player player, FileConfiguration itemConfig, List<String> itemStringList){
        String uuidString = player.getUniqueId().toString();
        FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);
        int max = getAllItemChange(itemStringList);
        int min = 1;

        int i = (int) (Math.random() * (max - min + 1) + min);

        ItemStack itemStackIn = ItemChangeSet.item_Map.get(i);

        int maxGet = 100;
        int minGet = 0;
        int getChange = itemConfig.getInt("ChanceCatchingItem");
        int randomChange = (int) (Math.random() * (maxGet - minGet + 1) + minGet);
        if(randomChange <= getChange){
            if(itemStackIn != null && fishingMain.checkBagFull()){
                ItemStack itemStack = itemStackIn.clone();
                int itemAmount = getAmount(ItemChangeSet.item_Amount_Map.get(itemStack));
                itemStack.setAmount(itemAmount);

                ItemStack[] itemStacks = Manager.player_item.get(uuidString);

                Arrays.stream(itemStacks).filter(Objects::isNull).limit(1).forEach(itemStack1->itemStacks[Arrays.asList(itemStacks).indexOf(itemStack1)]=itemStack);

                fishingMain.runAction("Caught-Item", player);

                String name = itemStack.getType().toString();
                if(itemStack.getItemMeta() != null && !itemStack.getItemMeta().getDisplayName().isEmpty()){
                    name = itemStack.getItemMeta().getDisplayName();
                }
                player.sendTitle(" ","釣到了"+name+" : "+itemAmount,10,40,40);
                //FancyFishing.fancyFishing.getLogger().info(i+" : "+name+" : "+itemAmount);
                GuiseEntity guiseEntity = GuiseEntity.createGuise( Look.getLook(player, 1));
                guiseEntity.setVisible(true);
                guiseEntity.appendItem16(itemStack, "HEAD");
                new GetItem(uuidString, guiseEntity, fishingMain.buoy, Look.getLook(player, 3).add(0,-2,0));
            }
        }else {
            fishingMain.runAction("Caught-No-Item", player);
        }
    }

    //獲取物品數量
    public static int getAmount(String itemString){
        int amount = 1;
        if(itemString != null){
            if(itemString.contains("-")){
                String[] aArray = itemString.split("-");
                if(aArray.length == 2){
                    if(NumberJudgment.isNumber(aArray[0]) && NumberJudgment.isNumber(aArray[1])){
                        int max = Integer.parseInt(aArray[1]);
                        int min = Integer.parseInt(aArray[0]);
                        if(max > min){
                            amount = (int) (Math.random() * (max - min + 1) + min);
                        }
                    }
                }
            }else {
                if(NumberJudgment.isNumber(itemString)){
                    amount = Integer.parseInt(itemString);
                }
            }
        }
        return amount;
    }

}
