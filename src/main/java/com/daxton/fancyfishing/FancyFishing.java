package com.daxton.fancyfishing;

import com.daxton.fancyfishing.config.FileConfig;
import com.daxton.fancyfishing.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FancyFishing extends JavaPlugin {

    public static FancyFishing fancyFishing;

    @Override
    public void onEnable() {
        fancyFishing = this;

        //前置插件
        if(!DependPlugins.depend()){
            fancyFishing.setEnabled(false);
            fancyFishing.onDisable();
            return;
        }
        //設定檔
        FileConfig.execute();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), fancyFishing);
        //this.getLogger().info("釣魚插件2");

    }

    @Override
    public void onDisable() {

    }
}
