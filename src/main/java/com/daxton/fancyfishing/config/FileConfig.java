package com.daxton.fancyfishing.config;

import com.daxton.fancycore.api.config.ConfigCreate;
import com.daxton.fancycore.api.config.ConfigLoad;
import com.daxton.fancyfishing.FancyFishing;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class FileConfig {
    //設定檔地圖
    public static Map<String, FileConfiguration> config_Map = new HashMap();

    public static void execute(){
        //建立設定檔
        ConfigCreate.execute(FancyFishing.fancyFishing);

        //讀取設定檔
        config_Map = ConfigLoad.execute(FancyFishing.fancyFishing);
    }
    //重新讀取設定檔
    public static void reload(){
        config_Map = ConfigLoad.execute(FancyFishing.fancyFishing);
    }

}
