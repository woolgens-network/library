package net.woolgens.library.spigot.gui;

import lombok.Getter;
import net.woolgens.library.common.concurrent.AsyncDispatcher;
import net.woolgens.library.common.concurrent.impl.JavaThreadPoolDispatcher;
import net.woolgens.library.common.event.EventBus;
import net.woolgens.library.spigot.gui.button.Button;
import net.woolgens.library.spigot.gui.button.StatefulButton;
import net.woolgens.library.spigot.gui.button.StatelessButton;
import net.woolgens.library.spigot.gui.button.event.ButtonEvent;
import net.woolgens.library.spigot.gui.pattern.Pattern;
import net.woolgens.library.spigot.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class GUI {

    public static final Map<UUID, GUI> OPENED_GUI = new HashMap<>();
    private static final AsyncDispatcher DISPATCHER = new JavaThreadPoolDispatcher();

    private GUIProperties properties;

    private Map<String, Object> state;
    private Map<Integer, Button> buttons;

    private EventBus eventBus;

    private Pattern pattern;

    private Inventory output;

    public GUI(GUIProperties properties) {
        this.properties = properties;
        this.state = new HashMap<>();
        this.buttons = new HashMap<>();
        this.eventBus = new EventBus();
    }

    public GUI setPattern(Pattern pattern) {
        this.pattern = pattern;
        return this;
    }

    public GUI(String title, int rows) {
        this(new GUIProperties());
        this.properties.setTitle(title);
        this.properties.setRows(rows);
    }

    public GUI(String title, InventoryType type) {
        this(new GUIProperties());
        this.properties.setTitle(title);
        this.properties.setType(type);
    }

    public GUI updateViewers() {
        if(this.output != null) {
            for(HumanEntity viewer : output.getViewers()) {
                if(viewer instanceof Player) {
                    Player player = (Player)viewer;
                    player.updateInventory();
                }
            }
        }
        return this;
    }

    public GUI clearButtons(int[] slots) {
        for(int slot : slots) {
            if(this.output != null) {
                this.output.setItem(slot, null);
            }
            if(this.buttons.containsKey(slot)) {
                this.buttons.remove(slot);
            }
        }
        return this;
    }

    public GUI fillInventory(Material filling) {
        this.properties.setFillInventory(new ItemBuilder(filling).setName("§c ").build());
        return this;
    }

    public GUI fillInventory() {
        return fillInventory(Material.GRAY_STAINED_GLASS_PANE);
    }

    public GUI removeButton(int slot) {
        this.buttons.remove(slot);
        return this;
    }

    public GUI addButton(Button button) {
        this.buttons.put(button.getSlot(), button);
        return this;
    }

    public GUI addStatelessButton(int slot, ItemStack item) {
        this.buttons.put(slot, new StatelessButton(slot, item, new ButtonEvent() {}));
        return this;
    }

    public GUI addStatelessButton(int slot, ItemStack item, ButtonEvent event) {
        this.buttons.put(slot, new StatelessButton(slot, item, event));
        return this;
    }

    public GUI addStatefulButton(int slot, StatefulButton.StateItemMaker maker, String... states) {
        this.buttons.put(slot, new StatefulButton(slot, maker, new ButtonEvent() {}, states));
        return this;
    }

    public GUI addStatefulButton(int slot, StatefulButton.StateItemMaker maker, ButtonEvent event, String... states) {
        this.buttons.put(slot, new StatefulButton(slot, maker, event, states));
        return this;
    }

    @Nullable
    public Button getButton(int slot) {
        return buttons.get(slot);
    }

    public boolean existsButton(int slot) {
        return buttons.containsKey(slot);
    }

    public GUI updateButton(int slot, ItemStack item) {
        if(existsButton(slot)) {
            Button button = getButton(slot);
            ItemStack place = button.setItem(item);
            if(output != null) {
                output.setItem(slot, place);
            }
        }
        return this;
    }

    public GUI setState(String state, Object value) {
        setLazyState(state, value);
        for(Button button : buttons.values()) {
            if(button instanceof StatefulButton stateful) {
                if(stateful.getSubscribed().contains(state)) {
                    updateButton(button.getSlot(), stateful.getMaker().make());
                }
            }
        }
        return this;
    }

    public String useState(String state, Object initValue) {
        setLazyState(state, initValue);
        return state;
    }

    public GUI setLazyState(String state, Object value) {
        this.state.put(state, value);
        return this;
    }

    public <T> T getState(String state) {
        return (T) this.state.get(state);
    }

    public GUI openInventory(Player player) {
        if(output != null) {
            player.openInventory(output);
            GUI.OPENED_GUI.put(player.getUniqueId(), this);
        }
        return this;
    }

    public GUI openInventory(Player player, Sound sound, float volume) {
        openInventory(player);
        player.playSound(player.getLocation(), sound, volume, 1);
        return this;
    }

    public GUI openInventory(Player player, Sound sound) {
        return openInventory(player, sound, 1);
    }

    public GUI buildButtons() {
        if(output == null) {
            return this;
        }
        if(properties.isAsync()) {
            DISPATCHER.dispatch(() -> {
                for(Button button : buttons.values()) {
                    output.setItem(button.getSlot(), button.getItem());
                }
            });
            return this;
        }
        for(Button button : buttons.values()) {
            output.setItem(button.getSlot(), button.getItem());
        }
        return this;
    }

    public GUI build() {
        output = properties.getType() != null ? Bukkit.createInventory(null, properties.getType(), properties.getTitle()) :
                Bukkit.createInventory(null, 9 * properties.getRows(), properties.getTitle());
        if(properties.getFillInventory() != null) {
            for (int i = 0; i < output.getSize(); i++) {
                output.setItem(i, properties.getFillInventory());
            }
        }
        if(pattern != null) {
            pattern.render(this, output);
        }
        buildButtons();
        return this;
    }
}
