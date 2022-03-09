package net.woolgens.library.spigot.hologram;

import lombok.Data;
import org.bukkit.entity.Player;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
public class HologramViewer {

    private Player player;
    private boolean spawned;

    public HologramViewer(Player player) {
        this.player = player;
        this.spawned = false;
    }
}
