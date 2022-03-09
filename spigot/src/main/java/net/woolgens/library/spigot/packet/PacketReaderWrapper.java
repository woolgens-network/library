package net.woolgens.library.spigot.packet;

import lombok.Getter;
import net.woolgens.library.spigot.hologram.Hologram;
import net.woolgens.library.spigot.packet.listener.PacketReaderListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class PacketReaderWrapper {

    private PacketReader reader;

    public PacketReaderWrapper(Plugin plugin) {
        this.reader = new PacketReader(plugin, "defaultpacketreader");
        for(Player player : Bukkit.getOnlinePlayers()) {
            reader.inject(player);
        }
        plugin.getServer().getPluginManager().registerEvents(new PacketReaderListener(this), plugin);

        Hologram.init(plugin, this);
    }

    public void disable() {
        reader.uninjectAll();
    }
}
