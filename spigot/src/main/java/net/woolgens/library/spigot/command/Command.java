package net.woolgens.library.spigot.command;

import net.woolgens.library.spigot.command.exception.CommandException;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface Command<T, A extends Arguments> {

    boolean send(T player, A arguments) throws CommandException;
}
