package com.colink02dev.mobstockmarket;

import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.TimeSkipEvent;

import java.util.Currency;
import java.util.Locale;

public class EntityKilled implements Listener {

    @EventHandler
    public void onEntityKilled(EntityDeathEvent e) {
        if(e.getEntity().getKiller() != null) {
            Player player = e.getEntity().getKiller();
            MobKills kills;
            if((kills = MobStockMarket.mobKills.get(e.getEntity().getType())) != null) {
                kills.addKill();
                double payment = kills.getPayment();
                if(payment > 0) {
                    MobStockMarket.eco.depositPlayer(player, payment);
                    player.sendActionBar(Component.text(String.format("You got $%.2f from killing a %s", payment, e.getEntity().getType().getKey().getKey())));
                } else {
                    EconomyResponse response = MobStockMarket.eco.withdrawPlayer(player, payment);
                    if(!response.transactionSuccess()) {
                        double accountMoney = MobStockMarket.eco.getBalance(player);
                        MobStockMarket.eco.withdrawPlayer(player, accountMoney);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onTimeSkip(TimeSkipEvent e) {
        if(e.getSkipReason().equals(TimeSkipEvent.SkipReason.NIGHT_SKIP)) {
            MobStockMarket.market.determineMobPrices();
        }
        e.setCancelled(false);
    }

}
