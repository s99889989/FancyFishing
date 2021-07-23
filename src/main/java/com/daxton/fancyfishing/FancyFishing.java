package com.daxton.fancyfishing;

import com.daxton.fancyfishing.command.MainCommand;
import com.daxton.fancyfishing.command.TabCommand;
import com.daxton.fancyfishing.config.FileConfig;
import com.daxton.fancyfishing.fishing.animation.BuoyFloating;
import com.daxton.fancyfishing.listener.PlayerListener;

import com.daxton.fancyfishing.taxk.Reload;
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
        //浮標上下浮動
        new BuoyFloating();
        //重新讀取用程序
        Reload.execute();
    }

    @Override
    public void onDisable() {

    }
}
