package net.woolgens.library.spigot.hologram;

import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class HologramLine {

    private String message;
    @Setter
    private Location location;
    private EntityArmorStand stand;
    private Map<EnumItemSlot, net.minecraft.world.item.ItemStack> equipment;
    @Setter
    private boolean small;

    @Setter
    private float offsetY;

    public HologramLine(String message, Location location) {
        this.message = message;
        this.location = location;
        this.offsetY = 0;
    }

    public int getEntityId() {
        return stand.getId();
    }

    public void update(Player player, String message) {
        this.message = message;
        this.stand.setCustomName(new ChatComponentText(message));
        sendMetaData(player);
    }

    public HologramLine addEquipment(EnumItemSlot slot, org.bukkit.inventory.ItemStack stack) {
        if(equipment == null) {
            equipment = new HashMap<>();
        }
        equipment.put(slot, CraftItemStack.asNMSCopy(stack));
        return this;
    }

    public void build() {
        WorldServer world = ((CraftWorld)location.getWorld()).getHandle();

        stand = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());

        if(!message.isEmpty()) {
            stand.setCustomNameVisible(true);
            stand.setCustomName(new ChatComponentText(message));
        }
        stand.setSmall(small);
        stand.setInvulnerable(true);
        stand.setInvisible(true);
        stand.setNoGravity(true);
    }

    public void teleport(Player player, Location location) {
        this.location = location;

        stand.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        PacketPlayOutEntityTeleport teleport = new PacketPlayOutEntityTeleport(stand);
        sendPacket(player, teleport);
    }

    public void spawn(Player player) {
        PacketPlayOutSpawnEntityLiving entityLiving = new PacketPlayOutSpawnEntityLiving(stand);
        sendPacket(player, entityLiving);

        if(equipment != null) {
            List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> pairs = new ArrayList<>();
            for(Map.Entry<EnumItemSlot, net.minecraft.world.item.ItemStack> equip : equipment.entrySet()) {
                pairs.add(new Pair<>(equip.getKey(), equip.getValue()));
            }
            PacketPlayOutEntityEquipment equipmentPacket = new PacketPlayOutEntityEquipment(stand.getId(), pairs);
            sendPacket(player, equipmentPacket);
        }

        sendMetaData(player);

    }



    public void despawn(Player player) {
        PacketPlayOutEntityDestroy entityDestroy = new PacketPlayOutEntityDestroy(stand.getId());
        sendPacket(player, entityDestroy);
    }

    public void sendMetaData(Player player) {
        PacketPlayOutEntityMetadata entityMetadata = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
        sendPacket(player, entityMetadata);
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer)player).getHandle().b.sendPacket(packet);
    }
}
