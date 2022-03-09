package net.woolgens.library.spigot.gui.button;

import lombok.Getter;
import lombok.Setter;
import net.woolgens.library.spigot.gui.button.event.ButtonEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class StatefulButton implements Button {

    private int slot;
    private StateItemMaker maker;
    private ButtonEvent event;

    private Set<String> subscribed;

    private ItemStack item;

    public StatefulButton(int slot, StateItemMaker maker, ButtonEvent event, String... states) {
        this.slot = slot;
        this.maker = maker;
        this.item = maker.make();
        this.event = event;
        this.subscribed = new HashSet<>(Arrays.asList(states));
    }

    @Override
    public ItemStack setItem(ItemStack item) {
        this.item = item;
        return item;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    public interface StateItemMaker {

        ItemStack make();
    }
}
