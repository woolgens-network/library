package net.woolgens.library.spigot.hologram;

import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.woolgens.library.spigot.hologram.listener.HologramQuitListener;
import net.woolgens.library.spigot.packet.PacketReaderWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class Hologram {

    public static List<Hologram> HOLOGRAMS = new ArrayList<>();
    public static PacketReaderWrapper READER;
    private static Field ENTITY_ID_FIELD;
    private static final Map<UUID, Long> DELAY_CLICK = new HashMap<>();


    @SneakyThrows
    public static void init(Plugin plugin, PacketReaderWrapper reader) {
        READER = reader;
        ENTITY_ID_FIELD = PacketPlayInUseEntity.class.getDeclaredField("a");
        ENTITY_ID_FIELD.setAccessible(true);

        plugin.getServer().getPluginManager().registerEvents(new HologramQuitListener(), plugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Hologram hologram : Hologram.HOLOGRAMS) {
                    hologram.update();
                }
            }
        }.runTaskTimer(plugin, 40, 40);
    }


    private static final double SPACE_BETWEEN = 0.28;
    private static final double DISTANCE_BETWEEN_VIEWERS = 900;

    private boolean global;
    private Location location;
    private List<HologramLine> lines;
    private Map<UUID, HologramViewer> viewers;


    public Hologram(String[] lines, Location location, boolean global) {
        this(location, global);
        this.global = global;
        for(String line : lines) {
            addLine(line);
        }
        buildLines(false);
    }

    public Hologram(Location location, boolean global) {
        this.global = global;
        this.location = location;
        this.viewers = new ConcurrentHashMap<>();
        this.lines = new ArrayList<>();
    }

    public Hologram removeViewer(Player player) {
        if(!existsViewer(player)) {
            return this;
        }
        this.viewers.remove(player.getUniqueId());
        return this;
    }

    @SneakyThrows
    public Hologram setInteraction(HologramInteraction interaction) {
        if(READER != null) {
            READER.getReader().addListener((player, packet) -> {
                if(packet instanceof PacketPlayInUseEntity entity) {
                    try {
                        int id = (int)ENTITY_ID_FIELD.get(entity);
                        if(existsViewer(player)) {
                            HologramViewer viewer = getViewer(player);
                            if(viewer.isSpawned()) {
                                for(HologramLine line : getLines()) {
                                    if(id == line.getEntityId()) {
                                        if(DELAY_CLICK.containsKey(player.getUniqueId())) {
                                            long time = DELAY_CLICK.get(player.getUniqueId());
                                            if(System.currentTimeMillis() < time) {
                                                return true;
                                            }
                                        }
                                        DELAY_CLICK.put(player.getUniqueId(), System.currentTimeMillis() + 600);
                                        interaction.onInteract(player);
                                        return true;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }

                return true;
            });
        }
        return this;
    }

    public HologramViewer getOrCreate(Player player) {
        HologramViewer viewer;
        if(existsViewer(player)) {
            viewer = getViewer(player);
            viewer.setPlayer(player);
        } else {
            viewer = addViewer(player);
        }
        return viewer;
    }

    public HologramViewer addViewer(Player player) {
        HologramViewer viewer = new HologramViewer(player);
        this.viewers.put(player.getUniqueId(), viewer);
        return viewer;
    }

    public HologramViewer getViewer(Player player) {
        return this.viewers.get(player.getUniqueId());
    }

    public boolean existsViewer(Player player) {
        return this.viewers.containsKey(player.getUniqueId());
    }

    public void spawn() {
        Hologram.HOLOGRAMS.add(this);
        update();
    }

    public void spawnLines(HologramViewer viewer) {
        for(HologramLine line : lines) {
            line.spawn(viewer.getPlayer());
        }
    }

    public void update() {
        if(global) {
            for(Player online : Bukkit.getOnlinePlayers()) {
                if(!online.isOnline()) {
                    return;
                }
                if(!online.getWorld().equals(location.getWorld())) {
                    continue;
                }
                if(online.getLocation().distanceSquared(location) <= DISTANCE_BETWEEN_VIEWERS) {
                    HologramViewer viewer = getOrCreate(online);
                    if(!viewer.isSpawned()) {
                        spawnLines(viewer);
                        viewer.setSpawned(true);
                    }
                } else {
                    HologramViewer viewer = getOrCreate(online);
                    if(viewer.isSpawned()) {
                        destroy(viewer);
                        viewer.setSpawned(false);
                    }
                }
            }
        } else {
            for(HologramViewer viewer : viewers.values()) {
                if(!viewer.getPlayer().isOnline()) {
                    return;
                }
                if(!viewer.getPlayer().getWorld().equals(location.getWorld())) {
                    continue;
                }
                if(viewer.getPlayer().getLocation().distanceSquared(location) <= DISTANCE_BETWEEN_VIEWERS) {
                    if(!viewer.isSpawned()) {
                        spawnLines(viewer);
                        viewer.setSpawned(true);
                    }
                } else {
                    if(viewer.isSpawned()) {
                        destroy(viewer);
                        viewer.setSpawned(false);
                    }
                }
            }
        }
    }

    public HologramLine updateLine(int index, String message) {
        HologramLine line = lines.get(index);
        for(HologramViewer viewer : viewers.values()) {
            line.update(viewer.getPlayer(), message);
        }
        return line;
    }

    public HologramLine addLine(String message) {
        HologramLine line = new HologramLine(message, null);
        this.lines.add(line);
        return line;
    }

    public HologramLine addLine() {
        return addLine("");
    }

    public void buildLines(boolean reverse) {
        Location spawnLocation = location.clone();
        for(HologramLine hologramLine : lines) {
            hologramLine.setLocation(spawnLocation.add(0, SPACE_BETWEEN + hologramLine.getOffsetY(), 0));
            hologramLine.build();
        }
        if(reverse) {
            Collections.reverse(lines);
        }

    }

    public void teleport(Location location) {
        this.location = location;

        Location spawnLocation = location.clone();
        for(HologramViewer viewer : viewers.values()) {
            for(HologramLine line : lines) {
                line.teleport(viewer.getPlayer(), spawnLocation.add(0.0, SPACE_BETWEEN + line.getOffsetY(), 0.0));
            }
        }
    }

    public void destroy() {
        Hologram.HOLOGRAMS.remove(this);
        for(HologramViewer viewer : getViewers().values()) {
            destroy(viewer);
        }
        viewers.clear();

    }

    public void destroy(HologramViewer viewer) {
        for(HologramLine line : lines) {
            if(viewer.isSpawned()) {
                line.despawn(viewer.getPlayer());
            }
        }
    }

    public interface HologramInteraction {

        void onInteract(Player player);
    }


}
