package live.spog.pvpClumps;

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

}
