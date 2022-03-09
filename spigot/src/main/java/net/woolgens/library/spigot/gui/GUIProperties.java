package net.woolgens.library.spigot.gui;

import lombok.Data;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
public class GUIProperties {

    private String title;
    private int rows;

    private boolean globalCancel = true;
    private boolean globalNumberKeyCancel = true;
    private boolean async = false;

    private InventoryType type;
    private ItemStack fillInventory;
}
