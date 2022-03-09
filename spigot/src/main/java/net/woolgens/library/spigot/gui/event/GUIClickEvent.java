package net.woolgens.library.spigot.gui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.woolgens.library.common.event.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
@AllArgsConstructor
public class GUIClickEvent implements Event {

    private Player player;
    private InventoryClickEvent event;
}
