package net.woolgens.library.spigot.packet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.Getter;
import net.minecraft.network.protocol.Packet;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class PacketReader {

    private final Plugin plugin;
    private final String name;
    private List<PacketReaderListener> listeners;
    private Map<UUID, Player> injectedPlayers;

    public PacketReader(Plugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.listeners = new ArrayList<>();
        this.injectedPlayers = new ConcurrentHashMap<>();
    }

    public PacketReader addListener(PacketReaderListener listener) {
        this.listeners.add(listener);
        return this;
    }

    public PacketReader inject(Player player) {
        if(injectedPlayers.containsKey(player.getUniqueId())) {
            return this;
        }
        Channel channel = getChannelByPlayer(player);
        channel.pipeline().addAfter("decoder", name, new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, List<Object> out) throws Exception {
                boolean allow = true;
                for(PacketReaderListener listener : listeners) {
                    allow = listener.onRead(player, packet);
                }
                if(allow) {
                    out.add(packet);
                }
            }
        });
        injectedPlayers.put(player.getUniqueId(), player);
        return this;
    }

    public PacketReader uninject(Player player) {
        if(!injectedPlayers.containsKey(player.getUniqueId())) {
            return this;
        }
        Channel channel = getChannelByPlayer(player);
        ChannelPipeline pipeline = channel.pipeline();
        if(pipeline.get(name) != null) {
            pipeline.remove(name);
        }
        injectedPlayers.remove(player.getUniqueId());
        return this;
    }

    public PacketReader uninjectAll() {
        for(Player player : injectedPlayers.values()) {
            uninject(player);
        }
        return this;
    }

    private Channel getChannelByPlayer(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        Channel channel = craftPlayer.getHandle().b.a.k;
        return channel;
    }
}
