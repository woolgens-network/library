package net.woolgens.library.spigot.gui.template.paged;

import lombok.Getter;
import net.woolgens.library.spigot.gui.GUI;
import net.woolgens.library.spigot.gui.button.event.ButtonEvent;
import net.woolgens.library.spigot.gui.button.event.ClickButtonEvent;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public abstract class PagedGUI extends GUI {

    private PageProperties pageProperties;

    public PagedGUI(String title, int rows, int[] slots) {
        super(title, rows);
        this.pageProperties = new PageProperties(this, slots, rows);

    }

    private void render() {
        load();
        String currentPage = useState(pageProperties.getCurrentPageState(), 1);

        addStatelessButton(pageProperties.getNextItemSlot(), pageProperties.getNextItem(), (ClickButtonEvent) (player, event) -> {
            openNextPage();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1);
        });
        addStatelessButton(pageProperties.getPreviousItemSlot(), pageProperties.getPreviousItemNull(), (ClickButtonEvent) (player, event) -> {
            openPreviousPage();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1);
        });
        if(pageProperties.isMiddleItemAllowed()) {
            addStatefulButton(pageProperties.getMiddleItemSlot(), pageProperties.getMiddleItem(), currentPage);
        }
    }

    private void updateItems() {
        updateButton(pageProperties.getPreviousItemSlot(), getCurrentPage() >= 2 ? pageProperties.getPreviousItem() : pageProperties.getPreviousItemNull());
        clearButtons(pageProperties.getSlots());

        List<PagedItem> items = getCurrentPage() == 1 ? pageProperties.loadItems(0, pageProperties.getSlots().length) :
                pageProperties.loadItems(((getCurrentPage()-1) * pageProperties.getSlots().length), getCurrentPage() * pageProperties.getSlots().length);
        int slot = 0;
        for(PagedItem item : items) {
            item.setSlot(pageProperties.getSlots()[slot]);
            addButton(item);
            slot++;
        }
        buildButtons();
    }

    public PagedGUI openNextPage() {
        final int size = pageProperties.getItems().size();
        final int full = getCurrentPage() * pageProperties.getSlots().length;
        if(size > full) {
            setState(pageProperties.getCurrentPageState(), getCurrentPage() + 1);
            updateItems();
        }
        return this;
    }

    public PagedGUI openPreviousPage() {
        if(getCurrentPage() <= 1) {
            return this;
        }
        setState(pageProperties.getCurrentPageState(), getCurrentPage() - 1);
        updateItems();
        return this;
    }

    public PagedGUI update() {
        for(PagedItem item : pageProperties.getItems()) {
            removeButton(item.getSlot());
        }
        load();
        updateItems();
        return this;
    }

    public int getCurrentPage() {
        return getState(pageProperties.getCurrentPageState());
    }

    public PagedGUI addItem(PagedItem item) {
        this.pageProperties.getItems().add(item);
        return this;
    }

    public PagedGUI addItem(ItemStack stack) {
        return addItem(new PagedItem(stack, new ButtonEvent() {}));
    }

    public PagedGUI addItem(ItemStack stack, ButtonEvent event) {
        return addItem(new PagedItem(stack, event));
    }



    @Override
    public GUI build() {
        render();
        super.build();
        updateItems();
        return this;
    }

    public abstract void load();
}
