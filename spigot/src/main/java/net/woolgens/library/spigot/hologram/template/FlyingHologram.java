package net.woolgens.library.spigot.hologram.template;

import net.woolgens.library.spigot.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class FlyingHologram extends Hologram {

    private final Plugin plugin;

    public FlyingHologram(Plugin plugin, String[] lines, Location location, boolean global) {
        super(lines, location, global);
        this.plugin = plugin;
    }

    @Override
    public void spawn() {
        super.spawn();

        Location location = getLocation().clone();

        new BukkitRunnable() {
            int ticks = 0;
            @Override
            public void run() {
                if(ticks >= 30) {
                    cancel();
                    destroy();
                    return;
                }
                teleport(location.add(0.0, 0.05, 0.0));
                ticks++;

            }
        }.runTaskTimer(plugin, 1, 1);
    }
}
