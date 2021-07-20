package com.daxton.fancyfishing;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DependPlugins {

    public static boolean depend(){

        FancyFishing fancyFishing = FancyFishing.fancyFishing;

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyCore") != null && Bukkit.getPluginManager().isPluginEnabled("FancyCore")){
            fancyFishing.getLogger().info(ChatColor.GREEN+"Loaded FancyCore");
        }else {
            fancyFishing.getLogger().severe("*** FancyCore is not installed or not enabled. ***");
            fancyFishing.getLogger().severe("*** FancyItemsy will be disabled. ***");
            fancyFishing.getLogger().severe("*** FancyCore未安裝或未啟用。 ***");
            fancyFishing.getLogger().severe("*** FancyItems將被卸載。 ***");
            return false;
        }

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyItems") != null && Bukkit.getPluginManager().isPluginEnabled("FancyItems")){
            fancyFishing.getLogger().info(ChatColor.GREEN+"Loaded FancyItems");
        }else {
            fancyFishing.getLogger().severe("*** FancyItems is not installed or not enabled. ***");
            fancyFishing.getLogger().severe("*** FancyItemsy will be disabled. ***");
            fancyFishing.getLogger().severe("*** FancyItems未安裝或未啟用。 ***");
            fancyFishing.getLogger().severe("*** FancyItems將被卸載。 ***");
            return false;
        }

        return true;
    }

}
