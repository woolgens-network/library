package net.woolgens.library.spigot.command.spigot.template.help;

import lombok.Getter;
import lombok.Setter;
import net.woolgens.library.spigot.command.exception.CommandException;
import net.woolgens.library.spigot.command.spigot.SpigotArguments;
import net.woolgens.library.spigot.command.spigot.SpigotCommand;
import net.woolgens.library.spigot.command.spigot.SpigotSubCommand;
import org.bukkit.command.CommandSender;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
@Setter
public abstract class HelpSubCommand<T extends CommandSender> extends SpigotSubCommand<T> {

    private String usage;
    private String description;

    public HelpSubCommand(String name) {
        super(name);
        setUsage("");
        setDescription("");
    }

}
