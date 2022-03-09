package net.woolgens.library.spigot.location;

import org.bukkit.Chunk;
import org.bukkit.Location;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public final class LocationUtil {

    public static Location getCenterOfBlock(Location location) {
        return location.getBlock().getLocation().clone().add(0.5, 0.0, 0.5);
    }

    public static Location getCenterOfChunk(Chunk chunk) {
        Location center = new Location(chunk.getWorld(), chunk.getX() << 4, 64, chunk.getZ() << 4).add(7, 0, 7);
        center.setY(center.getWorld().getHighestBlockYAt(center));
        return center;
    }
}
