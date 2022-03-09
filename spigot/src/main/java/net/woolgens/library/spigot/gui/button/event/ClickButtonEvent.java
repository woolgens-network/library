package net.woolgens.library.spigot.gui.button.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface ClickButtonEvent extends ButtonEvent {

    void onClick(Player player, InventoryClickEvent event);
}
