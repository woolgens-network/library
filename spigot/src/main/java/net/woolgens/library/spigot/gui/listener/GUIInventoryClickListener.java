package net.woolgens.library.spigot.gui.listener;

import net.woolgens.library.spigot.gui.GUI;
import net.woolgens.library.spigot.gui.button.Button;
import net.woolgens.library.spigot.gui.button.event.ClickButtonEvent;
import net.woolgens.library.spigot.gui.event.GUIClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class GUIInventoryClickListener implements Listener {

    @EventHandler
    public void onCall(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (GUI.OPENED_GUI.containsKey(player.getUniqueId())) {
            GUI gui = GUI.OPENED_GUI.get(player.getUniqueId());
            if (event.getClick() == ClickType.NUMBER_KEY && gui.getProperties().isGlobalNumberKeyCancel()) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            }
            if (event.getCurrentItem() != null) {
                if (event.getClickedInventory() != null) {
                    if (event.getView().getTitle().equals(gui.getProperties().getTitle())) {
                        if (gui.getProperties().isGlobalCancel()) {
                            event.setCancelled(true);
                            event.setResult(Event.Result.DENY);
                        }
                        if (gui.getEventBus().hasSubscribers(GUIClickEvent.class)) {
                            gui.getEventBus().publish(new GUIClickEvent(player, event));
                        }
                        if (gui.existsButton(event.getRawSlot())) {
                            Button button = gui.getButton(event.getRawSlot());
                            if (button.getEvent() != null) {
                                if (button.getEvent() instanceof ClickButtonEvent clickButtonEvent) {
                                    clickButtonEvent.onClick(player, event);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


