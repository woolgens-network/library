package net.woolgens.library.spigot.gui.listener;

import net.woolgens.library.spigot.gui.GUI;
import net.woolgens.library.spigot.gui.event.GUICloseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class GUIInventoryCloseListener implements Listener {

    @EventHandler
    public void onCall(InventoryCloseEvent event) {
        if(event.getInventory() != null) {
            Player player = (Player) event.getPlayer();
            if(GUI.OPENED_GUI.containsKey(player.getUniqueId())) {
                GUI gui = GUI.OPENED_GUI.get(player.getUniqueId());
                if(event.getView().getTitle().equals(gui.getProperties().getTitle())) {
                    gui.getEventBus().publish(new GUICloseEvent(player));
                    GUI.OPENED_GUI.remove(player.getUniqueId());
                }
            }
        }
    }
}
