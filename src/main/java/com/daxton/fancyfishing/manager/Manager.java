package com.daxton.fancyfishing.manager;

import com.daxton.fancycore.api.task.GuiseEntity;
import com.daxton.fancyfishing.fishing.action.FishingMain;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {

    public static Map<String, FishingMain> fishing_Main_Map = new HashMap<>();

    public static Map<String, GuiseEntity> guise_Entity_Map = new HashMap<>();

    public static Map<String, Integer> player_bag_size = new HashMap<>();

    public static Map<String, List<ItemStack>> player_item = new HashMap<>();

}
