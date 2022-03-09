package net.woolgens.library.spigot.gui.template;

import lombok.Getter;
import net.woolgens.library.spigot.gui.GUI;
import net.woolgens.library.spigot.gui.GUIProperties;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class PlayerGUI extends GUI {

    private Player player;

    public PlayerGUI(GUIProperties properties, Player player) {
        super(properties);
        this.player = player;
    }

    public PlayerGUI(String title, int rows, Player player) {
        super(title, rows);
        this.player = player;
    }

    public PlayerGUI(String title, InventoryType type, Player player) {
        super(title, type);
        this.player = player;
    }

    public PlayerGUI openInventory() {
        openInventory(player);
        return this;
    }

    public PlayerGUI openInventory(Sound sound) {
        return openInventory(sound, 1f);
    }

    public PlayerGUI openInventory(Sound sound, float volume) {
        openInventory(player, sound, volume);
        return this;
    }
}
