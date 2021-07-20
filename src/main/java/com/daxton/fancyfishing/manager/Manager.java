package com.daxton.fancyfishing.manager;

import com.daxton.fancycore.api.task.location.GuiseEntity;
import com.daxton.fancyfishing.fishing.real.FishingAction;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {

    public static Map<String, GuiseEntity> guise_Entity_Map = new HashMap<>();

    public static Map<String, FishingAction> fishing_Action_Map = new HashMap<>();

    public static Map<String, Integer> player_bag_size = new HashMap<>();

    public static Map<String, List<ItemStack>> player_item = new HashMap<>();

}
