package net.woolgens.library.spigot.gui.button;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.woolgens.library.spigot.gui.button.event.ButtonEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
@AllArgsConstructor
public class StatelessButton implements Button {

    private int slot;
    private ItemStack item;
    private ButtonEvent event;


    public ItemStack setItem(ItemStack item) {
        this.item = item;
        return item;
    }
}
