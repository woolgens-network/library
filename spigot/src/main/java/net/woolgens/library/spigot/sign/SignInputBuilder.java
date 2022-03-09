package net.woolgens.library.spigot.sign;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.function.Consumer;

public class SignInputBuilder {

    private final Plugin plugin;
    private Player player;
    private Location location;
    private BlockPosition position;

    private Consumer<List<String>> callback;

    private Channel channel;

    public SignInputBuilder(Plugin plugin, Player player, Consumer<List<String>> callback) {
        this.plugin = plugin;
        this.player = player;
        this.callback = callback;
        this.location = player.getLocation();
        this.position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    private SignInputBuilder createBlockData() {
        BlockData data = Bukkit.createBlockData(Material.OAK_SIGN);
        player.sendBlockChange(location, data);
        return this;
    }

    private SignInputBuilder openEditor() {
        PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(position);
        sendPacket(player, packet);
        destroy();
        inject();
        return this;
    }

    public SignInputBuilder open(String... lines) {
        createBlockData();
        player.sendSignChange(location, lines);
        return openEditor();
    }

    public SignInputBuilder open() {
        createBlockData();
        return openEditor();
    }



    private void inject() {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        this.channel = craftPlayer.getHandle().b.a.k;
        channel.pipeline().addAfter("decoder", "signinjector", new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, List<Object> out) throws Exception {
                out.add(packet);
                if(packet instanceof PacketPlayInUpdateSign) {
                    PacketPlayInUpdateSign updateSign = (PacketPlayInUpdateSign) packet;
                    if(callback != null) {
                        List<String> list = Lists.newArrayList();
                        for(String line : updateSign.c()) {
                            list.add(line);
                        }
                        Bukkit.getScheduler().runTask(plugin, () -> callback.accept(list));
                    }
                    destroy();
                    uninject();
                }

            }
        });
    }

    private void uninject() {
        if(channel != null) {
            if(channel.pipeline().get("signinjector") != null) {
                channel.pipeline().remove("signinjector");
            }
        }
    }

    private void destroy() {
        BlockData data = Bukkit.createBlockData(Material.AIR);
        player.sendBlockChange(location, data);
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer)player).getHandle().b.sendPacket(packet);
    }
}
