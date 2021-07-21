package com.daxton.fancyfishing;

import com.daxton.fancyfishing.command.MainCommand;
import com.daxton.fancyfishing.command.TabCommand;
import com.daxton.fancyfishing.config.FileConfig;
import com.daxton.fancyfishing.listener.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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
        //指令
        Objects.requireNonNull(Bukkit.getPluginCommand("fancyfishing")).setExecutor(new MainCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("fancyfishing")).setTabCompleter(new TabCommand());
        //監聽
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), fancyFishing);

    }

    @Override
    public void onDisable() {

    }
}
