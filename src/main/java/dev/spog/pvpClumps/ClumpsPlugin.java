package dev.spog.pvpClumps;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClumpsPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        initBstats();
    }

    @EventHandler(ignoreCancelled = true)
    public void onXP(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof ExperienceOrb orb)) return;
        int total = orb.getExperience();
        for (Entity e : orb.getNearbyEntities(3, 3, 3)) {
            if (!(e instanceof ExperienceOrb other)) continue;
            if (other == orb) continue;
            total += other.getExperience();
            other.remove();
        }
        orb.setExperience(total);
    }

    public void initBstats() {
        int pluginId = 30378;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(
                new SimplePie("chart_id", () -> "My value")
        );
        getLogger().info("Starting bStats instance using id: " + pluginId);
    }
}
