package com.colink02dev.mobstockmarket;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.w3c.dom.Text;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MarketPrices extends BukkitRunnable {

    private final Plugin plugin;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public MarketPrices(Plugin plugin) {
        this.plugin = plugin;
    }

    //Called every minecraft day
    public void determineMobPrices() {
        double marketFluctuation = random.nextDouble(0,0.5) * 500;
        for(Map.Entry<EntityType, MobKills> type: MobStockMarket.mobKills.entrySet()) {
            MobKills kills = type.getValue();
            TextColor color;
            if(kills.getKillCount() > marketFluctuation) {
                color = TextColor.fromHexString("#ff0000");
                kills.setPayment(kills.getPayment() - (marketFluctuation - kills.getKillCount()));
            } else {
                color = TextColor.fromHexString("#00ff00");
                kills.setPayment(kills.getPayment() + (marketFluctuation - kills.getKillCount()));
            }
            kills.resetKills();
            Bukkit.broadcast(Component.text(String.format("New Market Price for %s: $%.2f", type.getKey(), kills.getPayment())).color(color));
        }
        MobStockMarket.mobCount = 0;
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.GREEN + "The Market has fluctuated!");
        }
    }

    @Override
    public void run() {
        if(this.isCancelled()) return;
        if(Bukkit.getServer().getWorlds().get(0).getFullTime() == 0) {
            determineMobPrices();
        }
    }
}
