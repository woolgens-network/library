package net.woolgens.library.spigot.packet.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.woolgens.library.spigot.packet.PacketReaderWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@AllArgsConstructor
public class PacketReaderListener implements Listener {

    private PacketReaderWrapper wrapper;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        wrapper.getReader().inject(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        wrapper.getReader().uninject(event.getPlayer());
    }
}
