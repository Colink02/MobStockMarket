package com.colink02dev.mobstockmarket;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attributable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;

public final class MobStockMarket extends JavaPlugin {

    public static Economy eco;
    private static BukkitTask marketReEvaluationTask;
    public static final HashMap<EntityType, MobKills> mobKills = new HashMap<>();
    public static int mobCount = 0;
    public static MarketPrices market;

    @Override
    public void onEnable() {
        // Load Vault
        try {
            RegisteredServiceProvider<Economy> ecoRegistration = Bukkit.getServicesManager().getRegistration(Economy.class);
            eco = ecoRegistration.getProvider();
        } catch (NullPointerException e) {
            getLogger().warning("Economy is not loaded!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        getConfiguration();
        for(EntityType entity: EntityType.values()) {
            if(entity.getEntityClass() == null) continue;
            if(Attributable.class.isAssignableFrom(entity.getEntityClass())) {
                mobKills.put(entity, new MobKills(200.00));
            }
        }
        market = new MarketPrices(this);
        Bukkit.getPluginManager().registerEvents(new EntityKilled(), this);
        marketReEvaluationTask = market.runTaskTimerAsynchronously(this, 1, 1);
        market.determineMobPrices(); //Load mob prices from file
    }

    @Override
    public void onDisable() {
        marketReEvaluationTask.cancel();
    }

    public void getConfiguration() {
        FileConfiguration config = getConfig();
        /*for(EntityType entity: EntityType.values()) {
            ConfigurationSection mobSection = config.createSection("entity." + );
            mobSection.setComments("default_market_value", List.of("The Default Market Value on first plugin startup"));
            mobSection.set("default_market_value", 1.0);
            mobSection.set("default_market_payment", EntityRate.getEntityRate(entity));
        }*/
    }
}
