package com.daxton.fancyfishing.fishing.animation;

import com.daxton.fancycore.api.task.GuiseEntity;
import com.daxton.fancyfishing.FancyFishing;
import com.daxton.fancyfishing.fishing.FishingStatus;
import com.daxton.fancyfishing.fishing.action.FishingMain;
import com.daxton.fancyfishing.manager.Manager;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.function.Function;

public class OrbitalActionIn extends BukkitRunnable {

    private Location sourceLocation;
    private Location targetLocation;
    private GuiseEntity guiseEntity;
    private Vector vec;
    private int speed = 20;
    private int j;
    private int period = 1;
    private int duration = 100;
    private Function<Location,Location> fLocation;
    private String uuidString;

    public OrbitalActionIn(String uuidString,GuiseEntity guiseEntity, Location sourceLocation, Location targetLocation){
        this.guiseEntity = guiseEntity;
        this.sourceLocation = sourceLocation;
        this.targetLocation = targetLocation;
        this.fLocation = (floc) -> floc;
        this.uuidString = uuidString;
        vec = getDirection2(sourceLocation, true, true, false, 60, 180, 1);
        runTaskTimer(FancyFishing.fancyFishing, 0L, period);
    }

    public void run(){
        j += period;
        for(int k = 0; k < 1; ++k) {
            double c = Math.min(1.0D, (double) j / speed);
            Location location = fLocation.apply(sourceLocation.add(targetLocation.clone().subtract(sourceLocation).toVector().normalize().multiply(c).add(vec.multiply(1.0D - c))));
            guiseEntity.teleport(location);
        }

        if(j > duration || sourceLocation.distanceSquared(targetLocation) < 0.8D){
            cancel();
            guiseEntity.delete();
            Manager.guise_Entity_Map.remove(uuidString);
            FishingMain fishingMain = Manager.fishing_Main_Map.get(uuidString);
            fishingMain.execute(FishingStatus.PLAYER);
        }
    }

    public void onWater(){
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                guiseEntity.delete();
            }
        };
        bukkitRunnable.runTaskLater(FancyFishing.fancyFishing, 60);
    }

    //偏轉
    public static Vector getDirection2(Location dirLocation, boolean pt, boolean yw, boolean sign, double hight, double angle, double distance){
        Random random = new Random();
        if(sign){
            angle *= random.nextBoolean() ? 1 : -1;
        }
        double pitch;
        if(pt){
            pitch = ((dirLocation.getPitch() + 90 + (hight*-1)) * Math.PI) / 180;
        }else {
            pitch = ((90 + (hight*-1)) * Math.PI) / 180;
        }
        double yaw;
        if(yw){
            yaw  = ((dirLocation.getYaw() + 90 + angle)  * Math.PI) / 180;
        }else {
            yaw  = (90 + (angle)  * Math.PI) / 180;
        }

        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, y, z).multiply(distance);


        return vector;
    }

}
