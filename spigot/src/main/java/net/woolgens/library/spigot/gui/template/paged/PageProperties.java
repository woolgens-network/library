package net.woolgens.library.spigot.gui.template.paged;

import lombok.Data;
import net.woolgens.library.spigot.gui.button.StatefulButton;
import net.woolgens.library.spigot.gui.button.StatelessButton;
import net.woolgens.library.spigot.item.CustomSkullBuilder;
import net.woolgens.library.spigot.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
public class PageProperties {

    public static final int[] HOLLOW_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43
    };

    private static final ItemStack NEXT_ITEM_DEFAULT = new CustomSkullBuilder("http://textures.minecraft.net/texture/d34ef0638537222b20f480694dadc0f85fbe0759d581aa7fcdf2e43139377158")
            .setName("§8» §aNext page").build();
    private static final ItemStack PREVIOUS_ITEM_DEFAULT = new CustomSkullBuilder("http://textures.minecraft.net/texture/f7aacad193e2226971ed95302dba433438be4644fbab5ebf818054061667fbe2")
            .setName("§8» §ePrevious page").build();
    private static final ItemStack PREVIOUS_ITEM_NULL_DEFAULT = new ItemBuilder(Material.BARRIER).setName("§8» §cNo previous page").build();


    private int[] slots;
    private List<PagedItem> items;

    private int nextItemSlot;
    private ItemStack nextItem;

    private int previousItemSlot;
    private ItemStack previousItem;
    private ItemStack previousItemNull;

    private int middleItemSlot;
    private StatefulButton.StateItemMaker middleItem;

    public PageProperties(PagedGUI gui, int[] slots, int rows) {
        this.slots = slots;
        this.items = new ArrayList<>();

        int maxSize = rows * 9 - 1;

        this.nextItemSlot = maxSize;
        this.previousItemSlot = maxSize - 8;

        this.nextItem = NEXT_ITEM_DEFAULT.clone();
        this.previousItem = PREVIOUS_ITEM_DEFAULT.clone();
        this.previousItemNull = PREVIOUS_ITEM_NULL_DEFAULT.clone();

        this.middleItemSlot = maxSize-4;
        this.middleItem = () -> new ItemBuilder(Material.ENDER_EYE).setName("§8» §6Page§8: §3" + gui.getState(getCurrentPageState())).build();
    }

    public List<PagedItem> loadItems(int from, int to){
        List<PagedItem> newList = new ArrayList<>();
        for (int i = from; i < to; i++) {
            if(getItems().size() > i){
                if(getItems().get(i) != null){
                    newList.add(getItems().get(i));
                }
            }
        }
        return newList;
    }

    public String getCurrentPageState() {
        return "currentpage";
    }

    public boolean isMiddleItemAllowed() {
        return this.middleItemSlot != -1;
    }
}
