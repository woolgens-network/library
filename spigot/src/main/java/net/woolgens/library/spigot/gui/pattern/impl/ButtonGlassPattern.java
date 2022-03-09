package net.woolgens.library.spigot.gui.pattern.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.woolgens.library.spigot.gui.GUI;
import net.woolgens.library.spigot.gui.pattern.Pattern;
import net.woolgens.library.spigot.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@AllArgsConstructor
@Getter
public class ButtonGlassPattern implements Pattern {

    private static final ItemStack BUTTON = new ItemBuilder(Material.STONE_BUTTON).setName("§c ").build();

    private Material glassMaterial;

    @Override
    public void render(GUI gui, Inventory inventory) {
        int maxSize = inventory.getSize()-1;

        inventory.setItem(0, BUTTON);
        inventory.setItem(8, BUTTON);
        inventory.setItem(maxSize, BUTTON);
        inventory.setItem(maxSize-8, BUTTON);

        final ItemStack glass = new ItemBuilder(glassMaterial).setName("§c ").build();

        inventory.setItem(maxSize-9, glass);
        inventory.setItem(maxSize-1, glass);

        inventory.setItem((maxSize-8)-9 , glass);
        inventory.setItem((maxSize-7) , glass);

        inventory.setItem(1, glass);
        inventory.setItem(9, glass);

        inventory.setItem(17, glass);
        inventory.setItem(7, glass);
    }

}
