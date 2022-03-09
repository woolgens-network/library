package net.woolgens.library.spigot.npc;

import lombok.SneakyThrows;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.woolgens.library.spigot.packet.PacketReader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class NPCProcessor implements Listener {

    private static final double DISTANCE = 900;
    private static final double LOOK_DISTANCE = 100;
    private static final Map<UUID, Long> DELAY_CLICK = new HashMap<>();
    public static final List<NPCWrapper> NPCS = new ArrayList<>();
    private static PacketReader READER;
    private static Field ENTITY_ID_FIELD;

    public static void disable() {
        READER.uninjectAll();
    }


    @SneakyThrows
    public static void start(Plugin plugin) {
        ENTITY_ID_FIELD = PacketPlayInUseEntity.class.getDeclaredField("a");
        ENTITY_ID_FIELD.setAccessible(true);

        plugin.getServer().getPluginManager().registerEvents(new NPCProcessor(), plugin);

        READER = new PacketReader(plugin, "NPCS");
        READER.addListener((player, packet) -> {
            try {
                if(packet instanceof PacketPlayInUseEntity entity) {
                    int id = (int) ENTITY_ID_FIELD.get(entity);
                    for(NPCWrapper wrapper : NPCS) {
                        if(wrapper.getNpc().getEntityID() == id) {
                            if(DELAY_CLICK.containsKey(player.getUniqueId())) {
                               long time = DELAY_CLICK.get(player.getUniqueId());
                               if(System.currentTimeMillis() < time) {
                                   return true;
                               }
                            }
                            DELAY_CLICK.put(player.getUniqueId(), System.currentTimeMillis() + 1000);
                            wrapper.getListener().onClick(player);
                            return true;
                        }
                    }
                }
            }catch (Exception exception) {
                exception.printStackTrace();
            }
            return true;
        });
        for(Player online : Bukkit.getOnlinePlayers()) {
            READER.inject(online);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for(NPCWrapper wrapper : NPCS) {
                    for(Player online : Bukkit.getOnlinePlayers()) {
                        if(online.getWorld().equals(wrapper.getNpc().getLocation().getWorld())) {
                            double distance = online.getLocation().distanceSquared(wrapper.getNpc().getLocation());
                            if(distance <= DISTANCE) {
                                if(!wrapper.getPlayers().containsKey(online.getUniqueId())) {
                                    wrapper.getPlayers().put(online.getUniqueId(), online);
                                    wrapper.getNpc().spawnNPC(online);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            wrapper.getNpc().removeFromTabList(online);
                                        }
                                    }.runTaskLater(plugin, 20);
                                    wrapper.getNpc().teleportNPC(online, wrapper.getNpc().getLocation(), true);
                                }
                                if(distance <= LOOK_DISTANCE) {
                                    wrapper.getNpc().lookAtPlayer(online, online);
                                }
                            } else {
                                if(wrapper.getPlayers().containsKey(online.getUniqueId())) {
                                    wrapper.getNpc().destroyNPC(online);
                                    wrapper.getPlayers().remove(online.getUniqueId());
                                }
                            }
                        } else {
                            if(wrapper.getPlayers().containsKey(online.getUniqueId())) {
                                wrapper.getNpc().destroyNPC(online);
                                wrapper.getPlayers().remove(online.getUniqueId());
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 40, 1);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        READER.inject(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        READER.uninject(player);
        for(NPCWrapper wrapper : NPCS) {
            if(wrapper.getPlayers().containsKey(player.getUniqueId())) {
                wrapper.getPlayers().remove(player.getUniqueId());
            }
        }
    }
}
