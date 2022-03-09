package net.woolgens.library.spigot.command.exception.impl;

import lombok.Getter;
import net.woolgens.library.spigot.command.exception.CommandException;
import org.bukkit.command.CommandSender;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class NoPermissionException extends CommandException {

    private CommandSender sender;

    public NoPermissionException(CommandSender sender) {
        super("Sender does not have enough permission");
        this.sender = sender;
    }

}
