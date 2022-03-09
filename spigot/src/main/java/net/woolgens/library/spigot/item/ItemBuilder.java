package net.woolgens.library.spigot.item;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ItemBuilder {

    private Material material;
    private String name;
    private int amount;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantment;
    private ItemFlag[] flags;
    private boolean unbreakable;


    public ItemBuilder(Material material) {
        this.material = material;
        this.amount = 1;
    }

    public ItemBuilder setUnbreakable(boolean value) {
        this.unbreakable = value;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setFlags(ItemFlag... flags) {
        this.flags = flags;
        return this;
    }

    public ItemBuilder addLore(String... lines) {
        if (this.lore == null)
            setLore(new ArrayList<>());
        for (String lore : lines)
            this.lore.add(lore);
        return this;
    }

    public ItemBuilder setEnchantment(Map<Enchantment, Integer> enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        if (this.enchantment == null)
            setEnchantment(new HashMap<>());
        this.enchantment.put(enchantment, Integer.valueOf(level));
        return this;
    }

    public ItemBuilder allFlags() {
        setFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS});
        return this;
    }

    public ItemBuilder glow() {
        addEnchantment(Enchantment.ARROW_DAMAGE, 1);
        allFlags();
        return this;
    }

    public ItemStack build() {
        ItemStack stack = new ItemStack(this.material, this.amount);
        ItemMeta meta = stack.getItemMeta();
        if (this.name != null)
            meta.setDisplayName(this.name);
        if (this.lore != null)
            meta.setLore(this.lore);
        if (this.enchantment != null)
            for (Map.Entry<Enchantment, Integer> entry : this.enchantment.entrySet())
                meta.addEnchant(entry.getKey(), ((Integer) entry.getValue()).intValue(), true);
        if (this.flags != null)
            meta.addItemFlags(this.flags);
        stack.setItemMeta(meta);
        return stack;
    }
}