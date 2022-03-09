package net.woolgens.library.spigot.gui.template.paged;

import net.woolgens.library.spigot.gui.button.StatelessButton;
import net.woolgens.library.spigot.gui.button.event.ButtonEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class PagedItem extends StatelessButton {

    public PagedItem(ItemStack item, ButtonEvent event) {
        super(0, item, event);
    }
}
