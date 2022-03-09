package net.woolgens.library.spigot.gui.button;

import net.woolgens.library.spigot.gui.button.event.ButtonEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface Button {

    int getSlot();

    ItemStack setItem(ItemStack item);
    ItemStack getItem();

    ButtonEvent getEvent();

}
