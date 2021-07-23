package com.daxton.fancyfishing.command;

import com.daxton.fancyfishing.config.FileConfig;
import com.daxton.fancyfishing.taxk.Reload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args){
        if(sender instanceof Player && !sender.isOp()){
            return true;
        }
        //重新讀取設定
        if(args[0].equalsIgnoreCase("reload") && args.length == 1) {
            //重新讀取設定
            FileConfig.reload();
            //重新讀取的一些程序
            Reload.execute();
        }

        return true;
    }

}
