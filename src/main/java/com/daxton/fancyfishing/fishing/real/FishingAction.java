package com.daxton.fancyfishing.fishing.real;

import com.daxton.fancycore.api.task.location.GuiseEntity;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.manager.Manager;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class FishingAction {

    private final Player player;
    private final String uuidString;
    public boolean fishing = false;
    private BukkitRunnable bukkitRunnable;
    private BukkitRunnable countDown;
    public Location buoy;

    public FishingAction(Player player){
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }

    public void execute(FishingStatus fishingStatus){
        if(fishingStatus == FishingStatus.WATER){
            start();
        }
        if(fishingStatus == FishingStatus.PLAYER){
            stop();
        }
    }

    //開始釣魚
    public void start(){
        List<ItemStack> itemStacks = Manager.player_item.get(uuidString);
        int bagSize = Manager.player_bag_size.get(uuidString);
        if(!fishing){
            if(itemStacks.size() < bagSize){
                if(countDown != null){
                    countDown.cancel();
                }
                if(bukkitRunnable != null){
                    bukkitRunnable.cancel();
                }
                fishing = true;
                //player.sendMessage("開始釣魚");
                player.sendTitle(" ","開始釣魚",10,40,40);

                bukkitRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        cancel();
                        count();
                    }
                };
                bukkitRunnable.runTaskLater(FancyFishing.fancyFishing, 20 * 5);
            }else {
                //player.sendMessage("背包滿了");
                player.sendTitle(" ","背包滿了",10,40,40);
            }

        }
    }

    //倒數
    public void count(){
        fishing = false;
        //player.sendMessage("停止釣魚");
        player.sendTitle(" ","停止釣魚",10,40,40);
        setItem();

        List<ItemStack> itemStacks = Manager.player_item.get(uuidString);
        int bagSize = Manager.player_bag_size.get(uuidString);
        if(itemStacks.size() < bagSize){
            BukkitRunnable bukkitRunnable1 = new BukkitRunnable() {
                @Override
                public void run() {
                    countDown = new BukkitRunnable() {
                        int i = 9;
                        @Override
                        public void run() {
                            if(i < 1){
                                cancel();
                                player.getLocation().getWorld().playSound(player.getLocation(), "getamped_fishing_throw", 1, 1);
                                BukkitRunnable bukkitRunnable2 = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        GuiseEntity guiseEntity = GuiseEntity.createGuise( getLook(player, 1));
                                        guiseEntity.setVisible(true);
                                        guiseEntity.appendItem16(CustomItem.valueOf("FishingTools", "buoy", 1), "HEAD");
                                        Manager.guise_Entity_Map.put(uuidString, guiseEntity);
                                        new OrbitalActionOut(uuidString, guiseEntity, guiseEntity.getLocation(), getLook(player, 15).add(0,-3,0));
                                    }
                                };
                                bukkitRunnable2.runTaskLater(FancyFishing.fancyFishing, 20);

                            }else {
                                //player.sendMessage(i+"秒後重新開始釣魚");
                                player.sendTitle(" ",i+"秒後重新開始釣魚",10,20,5);
                                i--;
                            }

                        }
                    };
                    countDown.runTaskTimer(FancyFishing.fancyFishing, 0 , 20);
                }
            };
            bukkitRunnable1.runTaskLater(FancyFishing.fancyFishing, 20);


        }else {
            //player.sendMessage("背包滿了");
            player.sendTitle(" ","背包滿了",10,40,40);
        }

    }


    //停止釣魚
    public void stop(){
        if(countDown != null){
            countDown.cancel();
        }
        if(bukkitRunnable != null){
            bukkitRunnable.cancel();
        }
        fishing = false;
        //player.sendMessage("停止釣魚");
        player.sendTitle(" ","停止釣魚",10,40,40);
    }

    public void setItem(){
        int max = 10;
        int min = 1;

        int i = (int) (Math.random() * (max - min + 1) + min);
        GuiseEntity guiseEntity2 = Manager.guise_Entity_Map.get(uuidString);
        new OrbitalActionStop(uuidString, guiseEntity2, guiseEntity2.getLocation(), getLook(player, 2));

        if(i < 8){
            List<ItemStack> itemStacks = Manager.player_item.get(uuidString);
            int bagSize = Manager.player_bag_size.get(uuidString);
            if(itemStacks.size() < bagSize){
                ItemStack itemStack = CustomItem.valueOf("Fish", "0", 1);
                itemStacks.add(itemStack);
                //player.sendMessage("釣到了"+itemStack.getItemMeta().getDisplayName());
                player.getLocation().getWorld().playSound(player.getLocation(), "getamped_fishing_get_item", 1, 1);
                player.sendTitle(" ","釣到了"+itemStack.getItemMeta().getDisplayName(),10,40,40);
                GuiseEntity guiseEntity = GuiseEntity.createGuise( getLook(player, 1));
                guiseEntity.setVisible(true);
                guiseEntity.appendItem16(itemStack, "HEAD");
                new GetItem(uuidString, guiseEntity, buoy, getLook(player, 3).add(0,-2,0));
            }
        }else {
            ItemStack itemStack = CustomItem.valueOf("FishingGui", "FishingNoItem", 1);
            player.sendTitle(" ",itemStack.getItemMeta().getDisplayName(),10,40,40);
            player.getLocation().getWorld().playSound(player.getLocation(), "getamped_fishing_no_item", 1, 1);
            //player.sendMessage(itemStack.getItemMeta().getDisplayName());
        }


    }

    public static Location getLook(LivingEntity self, int distance){

        Location location = self.getLocation();
        Vector vector = location.getDirection().multiply(distance);
        location.add(vector);

        return location;
    }

}
