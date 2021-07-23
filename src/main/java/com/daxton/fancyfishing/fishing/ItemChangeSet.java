package com.daxton.fancyfishing.fishing;

import com.daxton.fancycore.api.item.CItem;
import com.daxton.fancycore.api.judgment.NumberJudgment;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.config.FileConfig;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemChangeSet {


    //全部機率物品
    public static Map<Integer, ItemStack> item_Map = new ConcurrentHashMap<>();

    public static Map<ItemStack, String> item_Amount_Map = new ConcurrentHashMap<>();

    private int nowNumber = 1;

    //設置全部機率物品
    public ItemChangeSet(){
        if(FileConfig.config_Map.get("Items.yml") != null){
            FileConfiguration itemConfig = FileConfig.config_Map.get("Items.yml");
            List<String> itemStringList = itemConfig.getStringList("ItemList");
            item_Map.clear();
            setAllItemChangeMap(itemStringList);
            item_Amount_Map.forEach((itemStack, s) -> {
                String name = itemStack.getType().toString();
                if(itemStack.getItemMeta() != null && !itemStack.getItemMeta().getDisplayName().isEmpty()){
                    name = itemStack.getItemMeta().getDisplayName();
                }
                FancyFishing.fancyFishing.getLogger().info(name+" : " + s);
            });
//            item_Map.forEach((integer, itemStack) -> {
//                String name = itemStack.getType().toString();
//                if(itemStack.getItemMeta() != null && !itemStack.getItemMeta().getDisplayName().isEmpty()){
//                    name = itemStack.getItemMeta().getDisplayName();
//                }
//                FancyFishing.fancyFishing.getLogger().info(integer+" : "+name);
//
//            });
//            FancyFishing.fancyFishing.getLogger().info(getAllItemChange(itemStringList)+"");
        }
    }

    //丟入物品字串列表，獲取整個列表加總機率。
    public void setAllItemChangeMap(List<String> itemStringList){
        itemStringList.forEach(this::setItemChangeMap);
    }

    //丟入物品字串，獲取該物品機率。
    public int setItemChangeMap(String itemString){
        int count = 0;
        String[] itemStringArray = itemString.split(" ");
        ItemStack itemStack = null;
        if(itemStringArray.length == 3 && NumberJudgment.isNumber(itemStringArray[1])){
            itemStack = getItem(itemStringArray[0]);
            count = Integer.parseInt(itemStringArray[1]);
            item_Amount_Map.put(itemStack, itemStringArray[2]);
        }
        if(itemStringArray.length == 2 && NumberJudgment.isNumber(itemStringArray[1])){
            itemStack = getItem(itemStringArray[0]);
            count = Integer.parseInt(itemStringArray[1]);
        }
        if(itemStringArray.length == 1){
            itemStack = getItem(itemStringArray[0]);
            count = 1;
        }

        for(int i = 0; i < count; i++){
            item_Map.put(nowNumber++, itemStack);
        }
        return count;
    }



    //獲取物品
    public ItemStack getItem(String itemString){
        ItemStack itemStack = null;
        if(itemString.contains(".")){
            String[] customArray = itemString.split("\\.");
            if(customArray.length == 2){
                itemStack = CustomItem.valueOf(customArray[0], customArray[1], 1);
            }
        }else {
            CItem cItem = new CItem(itemString);
            itemStack = cItem.getItemStack();
        }
        return itemStack;
    }

}
