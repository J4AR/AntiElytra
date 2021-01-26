package me.itsjaar.antielytra;

import me.itsjaar.antielytra.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Main extends JavaPlugin{
    @Override
    public void onEnable(){
        getLogger().info("AntiElytra has been enabled!");
    }
    @Override
    public void onDisable(){
        getLogger().info("AntiElytra has been disabled!");
    }

    @EventHandler
    public void onMovement(PlayerMoveEvent event) {
        double tps = getTPS();
        if (tps < 15) {
            Player player = event.getPlayer();
            if (player.isGliding()) {
                player.setGliding(false);
                if (player.getInventory().getChestplate().getType() == Material.ELYTRA) {
                    ItemStack elytra = player.getInventory().getChestplate();
                    player.getWorld().dropItem(player.getLocation(), elytra);
                    player.getInventory().setChestplate(null);
                }
            }
        }
    }
    public static double getTPS() {
        double tps = 0.0;
        try {
            Object mc = ReflectionUtils.invokeMethod((Object) Bukkit.getServer(), "getServer", false);
            Field rec = ReflectionUtils.getField(mc, "recentTps", false);
            double[] recentTps = (double[]) rec.get(mc);
            tps = recentTps[0];
        } catch (Throwable throwable) {
            // empty catch block
        }
        return tps;
    }
}
