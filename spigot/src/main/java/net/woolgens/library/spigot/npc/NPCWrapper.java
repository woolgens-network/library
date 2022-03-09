package net.woolgens.library.spigot.npc;

import lombok.Data;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
public class NPCWrapper {

    private NPC npc;
    private boolean looking;
    private Map<UUID, Player> players;
    private NPCClickListener listener;

    public NPCWrapper(NPC npc) {
        this.npc = npc;
        this.players = new HashMap<>();
    }

    public NPCWrapper build() {
        NPCProcessor.NPCS.add(this);
        return this;
    }

    public NPCWrapper delete() {
        NPCProcessor.NPCS.remove(this);
        npc.destroyNPC(players.values());
        return this;
    }


    public interface NPCClickListener {

        void onClick(Player player);

    }

}
