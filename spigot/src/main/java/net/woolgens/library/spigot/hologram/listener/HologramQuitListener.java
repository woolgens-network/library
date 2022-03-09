package net.woolgens.library.spigot.hologram.listener;

import net.woolgens.library.spigot.hologram.Hologram;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class HologramQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        for(Hologram hologram : Hologram.HOLOGRAMS) {
            hologram.removeViewer(event.getPlayer());
        }
    }
}
